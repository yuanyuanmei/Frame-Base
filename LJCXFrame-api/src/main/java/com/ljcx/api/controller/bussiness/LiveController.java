package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.beans.TeamInfoBean;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.api.dto.CallDto;
import com.ljcx.api.dto.LiveDto;
import com.ljcx.api.dto.RoomUserDto;
import com.ljcx.api.service.TeamInfoService;
import com.ljcx.api.service.UavInfoService;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.live.common.profile.AppProfile;
import com.ljcx.framework.live.tencent.LiveUtil;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.service.UserBaseService;
import com.ljcx.user.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 直播controller
 */
@RestController
@RequestMapping("/live")
@Slf4j
public class LiveController {

    @Autowired
    private ActivemqProducer producer;

    @Autowired
    private UserBaseService userBaseService;
    
    @Autowired
    private UavInfoService uavInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TeamInfoService teamInfoService;

    /**
     * 获取推流地址和播放地址
     * @return
     */
    @PostMapping("/getPushAddress")
    @ValidateCustom(LiveDto.class)
    public ResponseInfo getPushAddress(@RequestBody String info) {
        LiveDto liveDto = JSONObject.parseObject(info, LiveDto.class);
        UavInfoBean uav = uavInfoService.getById(liveDto.getId());
        Random random = new Random();
        liveDto.setStreamName("room"+random.nextInt(10000));
        liveDto.setName(uav.getName());
        liveDto.setAction("play");
        //将播放地址存入redis
        redisTemplate.opsForValue().set(RedisConstant.UAV_LIVE_STATE_ +liveDto.getId(),JSONObject.toJSONString(liveDto),1800, TimeUnit.SECONDS);
        //将播放地址广播出去
        producer.sendTopic("topic_live_room",JSONObject.toJSONString(liveDto));
        return new ResponseInfo(liveDto.getPushAddress());
    }



    /**
     * 获取用户签名和房间号
     * @return
     */
    @PostMapping("/getRoomAndUserSign")
    @ValidateCustom(CallDto.class)
    public ResponseInfo getRoomAndUserSign(@RequestBody String info, HttpServletRequest request) {
        CallDto callDto = JSONObject.parseObject(info, CallDto.class);
        TeamInfoBean teamBean = teamInfoService.getById(callDto.getTeamId());
        List<RoomUserDto> roomUserList = new ArrayList<>();
        long callingUserId = JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN));
        UserBaseBean callingUser = userBaseService.getById(callingUserId);
        String callingUserName = callingUser.getUsername();
        String callingNickName = callingUser.getNickname();
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
                RoomUserDto calledRommUser = new RoomUserDto(calledUserDto.getUserId(),calledUserDto.getUserName());
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
                RoomUserDto calledRommUser = new RoomUserDto(calledUserDto.getUserId(),calledUserDto.getUserName());
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
        callDto.setCallingUser(callingUserId);
        callDto.setCallingName(callingUserName);
        callDto.setCallingNickName(callingNickName);
        callDto.setUserId(baseBean.getId());
        callDto.setUserSign(baseBean.getUserSig());
        callDto.setUserName(baseBean.getUsername());
        callDto.setRoomId(roomId);
        callDto.setType(type);
        callDto.setIsFail(isFail);
        return callDto;
    }

    /**
     * 解散房间
     * @return
     */
    @PostMapping("/dissolveRoom")
    public ResponseInfo dissolveRoom(@RequestBody String info,String token) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(info == null || jsonObject.getLong("roomId") == null){
            return new ResponseInfo().failed("房间号不能为空");
        }
        Long roomId = jsonObject.getLong("roomId");
        return new ResponseInfo(LiveUtil.dissolveRoom(roomId));
    }







}
