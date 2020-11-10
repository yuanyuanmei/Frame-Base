package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.platform.dto.LiveDto;
import com.ljcx.platform.dto.UavInfoDto;
import com.ljcx.platform.mgbean.UavInfoMongo;
import com.ljcx.platform.service.UavInfoService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.vo.UavInfoVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 无人机controller
 */
@Api(value = "无人机模块")
@RestController
@RequestMapping("/team/uav")
@Slf4j
public class UavInfoController {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UavInfoService uavInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ActivemqProducer producer;
    /**
     * 获取无人机状态
     * @param info
     * info(uavInfoDto 对象)
     * @return
     */
    @PostMapping("/getState")
    //@RequiresPermissions("team:equipment:query")
    public ResponseInfo getState(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getLong("teamId") == null){
            return new ResponseInfo().failed("团队Id不能为空");
        }
        return uavInfoService.getState(jsonObject.getLong("teamId"));
    }

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
        return new ResponseInfo(liveDto);
    }

    /**
     * 获取飞机动态信息
     * @return
     */
    @PostMapping("/planPanel")
    @ValidateCustom(UavInfoDto.class)
    public ResponseInfo planPanel(@RequestBody String info) {
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        return new ResponseInfo(uavInfoService.planPanel(uavInfoDto));
    }

    /**
     * 获取飞机动态信息
     * @return
     */
    @PostMapping("/saveRoutes")
    public ResponseInfo saveRoutes(@RequestBody String info) {
        UavInfoVo uavInfoVo = JSONObject.parseObject(info, UavInfoVo.class);
        return uavInfoService.saveRoutes(uavInfoVo);
    }

    /**
     * 获取飞机动态信息
     * @return
     */
    @PostMapping("/findRoutes")
    public ResponseInfo findRoutes(@RequestBody String info) {
        UavInfoMongo uavInfoMongo = JSONObject.parseObject(info, UavInfoMongo.class);
        return uavInfoService.findRoutes(uavInfoMongo);
    }


}
