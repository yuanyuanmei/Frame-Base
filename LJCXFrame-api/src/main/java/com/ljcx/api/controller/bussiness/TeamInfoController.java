package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.dto.CloseDto;
import com.ljcx.api.dto.LiveDto;
import com.ljcx.api.service.TeamInfoService;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.constants.UserConstants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 团队controller
 */
@Api(value = "团队模块")
@RestController
@RequestMapping("/team/list")
@Slf4j
public class TeamInfoController {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TeamInfoService teamInfoService;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ActivemqProducer producer;


    /**
     * 团队列表
     * @param token
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/list")
    //@RequiresPermissions("team:list:query")
    public ResponseInfo list(HttpServletRequest request) {
        return new ResponseInfo(teamInfoService.list(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN))));
    }

    /**
     * 设备分类数量统计
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/getDataNums")
    //@RequiresPermissions("team:list:query")
    public ResponseInfo getDataNums(@RequestBody String info,HttpServletRequest request) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getLong("teamId") == null){
            return new ResponseInfo().failed("团队Id不能为空");
        }
        return teamInfoService.getDataNums(jsonObject.getLong("teamId"),JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
    }

    /**
     * 团队信息
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/info")
    //@RequiresPermissions("team:list:query")
    @ValidateCustom(Integer.class)
    public ResponseInfo info(@RequestBody String info,HttpServletRequest request) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        return new ResponseInfo(teamInfoService.info(jsonObject.getLong("id"),JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN))));
    }

    /**
     * 关闭设备（飞机，人，车）
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/close")
    //@RequiresPermissions("team:list:del")
    @ValidateCustom(CloseDto.class)
    public ResponseInfo close(@RequestBody String info) {
        CloseDto closeDto = JSONObject.parseObject(info, CloseDto.class);
        if(closeDto.getType() == 4){
            LiveDto liveDto = new LiveDto();
            liveDto.setId(closeDto.getId());
            liveDto.setTeamId(closeDto.getTeamId());
            producer.sendTopic("topic_close_live",JSON.toJSONString(liveDto));
        }else{
            producer.sendTopic("topic_team_close",JSON.toJSONString(closeDto));
        }

        return new ResponseInfo().success("关闭成功");
    }



}
