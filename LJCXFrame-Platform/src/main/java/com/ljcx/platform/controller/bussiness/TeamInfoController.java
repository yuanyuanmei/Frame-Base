package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.live.common.profile.AppProfile;
import com.ljcx.framework.live.tencent.LiveUtil;
import com.ljcx.platform.beans.TeamInfoBean;
import com.ljcx.platform.dto.CallDto;
import com.ljcx.platform.dto.RoomUserDto;
import com.ljcx.platform.service.TeamInfoService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TeamInfoVo;
import com.ljcx.platform.vo.UavInfoVo;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.service.UserBaseService;
import com.ljcx.user.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 团队controller
 */
@RestController
@RequestMapping("/team/list")
@Slf4j
public class TeamInfoController {

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private ActivemqProducer producer;



    /**
     * 团队列表
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/list")
    //@RequiresPermissions("team:list:query")
    public ResponseInfo list() {
        return new ResponseInfo(teamInfoService.list(UserUtil.getCurrentUser().getId()));
    }

    /**
     * 团队列表
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/treeList")
    //@RequiresPermissions("team:list:query")
    public ResponseInfo treeList() {
        return new ResponseInfo(teamInfoService.treeList(UserUtil.getCurrentUser().getId()));
    }
    /**
     * 团队信息
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/info")
    //@RequiresPermissions("team:list:query")
    @ValidateCustom(Integer.class)
    public ResponseInfo info(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        return new ResponseInfo(teamInfoService.info(jsonObject.getLong("id")));
    }

    /**
     * 设备分类数量统计
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/getDataNums")
    //@RequiresPermissions("team:list:query")
    public ResponseInfo getDataNums(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getLong("teamId") == null){
            return new ResponseInfo().failed("团队Id不能为空");
        }
        return new ResponseInfo(teamInfoService.getDataNums(jsonObject.getLong("teamId")));
    }

    /**
     * 获得在线数量
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/onlineNums")
   //@RequiresPermissions("team:list:query")
    public ResponseInfo onlineNums(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getLong("teamId") == null){
            return new ResponseInfo().failed("团队Id不能为空");
        }
        Long teamId = jsonObject.getLong("teamId");
        long currentTime = System.currentTimeMillis();
        long totalOnlineNums = 0;
        List<MapVo> dataNums = teamInfoService.getDataNums(teamId);
        Map<String, Long> dataMap = dataNums.stream().collect(Collectors.toMap(MapVo::getName, MapVo::getValue));
        int totalNums = 0;
        for(MapVo item: dataNums){
            totalNums += item.getValue();
        }
        totalOnlineNums = getOnlineNums(teamId,currentTime);;
        JSONObject map = new JSONObject();
        map.put("totalOnlineNums",totalOnlineNums);
        map.put("totalNums",totalNums);
        map.put("dataNums",dataMap);
        return new ResponseInfo(map);
    }

    private long getOnlineNums(Long teamId,Long currentTime){
        Long uav_nums = redisTemplate.opsForZSet().count(RedisConstant.UAV_ONLINE_NUMS_+teamId,currentTime-5*60*1000,currentTime);
        Long car_nums = redisTemplate.opsForZSet().count(RedisConstant.CAR_ONLINE_NUMS_+teamId,currentTime-5*60*1000,currentTime);
        Long member_nums = redisTemplate.opsForZSet().count(RedisConstant.MEMBER_ONLINE_NUMS_+teamId,currentTime-5*60*1000,currentTime);
        return uav_nums+car_nums+member_nums;
    }

    /**
     * 获取用户签名和房间号
     * @return
     */
    @PostMapping("/getRoomAndUserSign")
    @ValidateCustom(CallDto.class)
    public ResponseInfo getRoomAndUserSign(@RequestBody String info) {
        CallDto callDto = JSONObject.parseObject(info, CallDto.class);
        TeamInfoBean teamBean = teamInfoService.getById(callDto.getTeamId());
        List<RoomUserDto> roomUserList = new ArrayList<>();
        long callingUserId = UserUtil.getCurrentUser().getId();
        String callingUserName = UserUtil.getCurrentUser().getUsername();
        String callingNickName = UserUtil.getCurrentUser().getNickname();
        CallDto resp = getCalledUser(callingUserId,callingUserName,callingNickName,callDto.getIsFail(),callingUserId,teamBean.getRoomId(),callDto.getType());
        resp.setTeamId(teamBean.getId());
        //发送消息给接收对象
        if(callDto.getType() == 1){
            //calledId 可能为多个用户id
            List<CallDto> callDtoList = new ArrayList<>();
            //添加发起者信息
            roomUserList.add(new RoomUserDto(callingUserId, callingUserName));
            for(int i = 0;i<callDto.getCalledId().length;i++){
                CallDto calledUserDto = getCalledUser(callingUserId,callingUserName,callingNickName,callDto.getIsFail(),callDto.getCalledId()[i],teamBean.getRoomId(),callDto.getType());
                RoomUserDto calledRommUser = new RoomUserDto(calledUserDto.getUserId(),calledUserDto.getUsername());
                roomUserList.add(calledRommUser);
                callDtoList.add(calledUserDto);
            }
            for(CallDto item:callDtoList){
                item.setRoomUserList(roomUserList);
                producer.sendTopic("topic_call_member",JSONObject.toJSONString(item));
            }
        }else{
            List<MemberVo> baseBeanList = userBaseService.listByTeamId(callDto.getTeamId());
            List<CallDto> callDtoList = new ArrayList<>();
            for(MemberVo item:baseBeanList){
                CallDto calledUserDto = getCalledUser(callingUserId,callingUserName,callingNickName,callDto.getIsFail(),item.getId(),teamBean.getRoomId(),callDto.getType());
                RoomUserDto calledRommUser = new RoomUserDto(calledUserDto.getUserId(),calledUserDto.getUsername());
                roomUserList.add(calledRommUser);
                callDtoList.add(calledUserDto);
            }
            for(CallDto item:callDtoList){
                item.setRoomUserList(roomUserList);
                producer.sendTopic("topic_call_member",JSONObject.toJSONString(item));
            }
        }
        resp.setRoomUserList(roomUserList);
        return new ResponseInfo(resp);
    }

    @SuppressWarnings("all")
    private UserBaseBean getUserSign(Integer isFail,Long userId){
        UserBaseBean baseBean = userBaseService.getById(userId);
        //判断签名是否为空并且是否过期
        if(isFail == 1
                || StringUtils.isEmpty(baseBean.getUserSig())
                || System.currentTimeMillis() > baseBean.getSignExpireTime().getTime()){
            String sign = LiveUtil.genSig(baseBean.getUsername());
            baseBean.setUserSig(sign);
            baseBean.setSignExpireTime(new Date(System.currentTimeMillis()+ AppProfile.SIGN_EXPIRE_TIME*1000));
            userBaseService.updateById(baseBean);
        }
        return baseBean;
    }

    private CallDto getCalledUser(Long callingUserId,String callingUserName,String callingNickName,Integer isFail,Long userId,Integer roomId,Integer type){
        CallDto callDto = new CallDto();
        UserBaseBean baseBean = getUserSign(isFail, userId);
        //返回签名对象给api
        callDto.setCallingNickName(callingNickName);
        callDto.setCallingUser(callingUserId);
        callDto.setCallingName(callingUserName);
        callDto.setUserId(baseBean.getId());
        callDto.setUserSign(baseBean.getUserSig());
        callDto.setUsername(baseBean.getUsername());
        callDto.setRoomId(roomId);
        callDto.setType(type);
        callDto.setIsFail(isFail);
        return callDto;
    }

    @PostMapping("/sendPlan")
    public ResponseInfo sendPlan() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",10l);
        jsonObject.put("name","丹妹妹的小飞机");
        jsonObject.put("batteryHealth",66);
        jsonObject.put("teamId",9l);
        jsonObject.put("speed",12.5);
        jsonObject.put("model","加油加油");
        jsonObject.put("high",21.2);
        jsonObject.put("electricity",99);
        jsonObject.put("status",1);
        jsonObject.put("position","123,123");
        jsonObject.put("canLive",1);
        producer.sendTopic("topic_uav_state", jsonObject.toJSONString());
        return new ResponseInfo().success("发送成功");
    }

    @PostMapping("/closePlan")
    public ResponseInfo closePlan() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",10l);
        jsonObject.put("name","丹妹妹的小飞机");
        jsonObject.put("batteryHealth",66);
        jsonObject.put("teamId",9l);
        jsonObject.put("speed",12.5);
        jsonObject.put("model","加油加油");
        jsonObject.put("high",21.2);
        jsonObject.put("electricity",99);
        jsonObject.put("status",0);
        jsonObject.put("canLive",0);
        producer.sendTopic("topic_uav_state", jsonObject.toJSONString());
        return new ResponseInfo().success("发送成功");
    }

}
