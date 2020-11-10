package com.ljcx.api.activemq;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.dto.LiveDto;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.framework.netty.ApplicationWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.IOException;


/**
 * 监听
 */
@Slf4j
@Component
public class LiveCustomer {

    @Autowired
    private ApplicationWebSocket webSocket;

    @Autowired
    private CommonCustomer common;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  监听队列名称为直播地址的主题
     */
    @JmsListener(destination = "topic_live_room",containerFactory = "jmsListenerContainerTopic")
    public void receiveLiveRoomTopic(Message message) throws IOException {
        Object info = common.getInfo(message);
        LiveDto liveDto = JSONObject.parseObject(info.toString(),LiveDto.class);
        common.sendTeamMessage(info,"plane_video",liveDto.getTeamId());
        log.info("丹妹温馨提示：直播地址 topic Consumer:{}",info.toString());
    }

    /**
     *  监听队列名称为直播地址的主题
     */
    @JmsListener(destination = "topic_close_live",containerFactory = "jmsListenerContainerTopic")
    public void receiveCloseLiveTopic(Message message) throws IOException {
        Object info = common.getInfo(message);
        LiveDto liveDto = JSONObject.parseObject(info.toString(),LiveDto.class);
        redisTemplate.delete(RedisConstant.UAV_LIVE_STATE_+liveDto.getId());
        common.sendTeamMessage(info,"plane_video",liveDto.getTeamId());
        log.info("丹妹温馨提示：关闭直播 topic Consumer:{}",info.toString());
    }

    /**
     *  监听队列名称为通话的主题
     */
    @JmsListener(destination = "topic_call_member",containerFactory = "jmsListenerContainerTopic")
    public void receiveCallMemberTopic(Message message) throws IOException {
        Object info = common.getInfo(message);
        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        common.sendSbMessage(info,"call",jsonObject.getLong("userId"));
        log.info("丹妹温馨提示：通话对象为 :{}",info.toString());
    }

    /**
     *  监听队列名称为通话的主题
     */
    @JmsListener(destination = "topic_call_state",containerFactory = "jmsListenerContainerTopic")
    public void receiveCallStateTopic(Message message) throws IOException {
        Object info = common.getInfo(message);
        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        common.sendSbMessage(info,"call_state",jsonObject.getLong("callingUser"));
        log.info("丹妹温馨提示：通话状态为 :{}",info.toString());
    }



}
