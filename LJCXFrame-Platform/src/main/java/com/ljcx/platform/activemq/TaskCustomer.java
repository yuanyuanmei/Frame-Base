package com.ljcx.platform.activemq;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 监听
 *
 * redis统计在线人数
 * 用zset存储上线用户
 * add(name,value,score) add(集合名称，用户，过期时间) 添加集合用户
 * remove(name,value...); remove(集合名称。用户...) 删除集合中特定用户
 * zCard(name); 获取集合中所有元素数量
 * rangeByScore(name,startTime,endTime) 获取时间范围内集合中用户列表
 * count(name,startTime,endTime) 获取时间范围内集合中用户数量
 */
@Slf4j
@Component
public class TaskCustomer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CommonCustomer common;


    /**
     *  监听任务日志的主题
     */
    @JmsListener(destination = "topic_task_log",containerFactory = "jmsListenerContainerTopic")
    public void receiveAddTaskTopic(Message message) throws IOException {
        Object info = common.getInfo(message);
        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        Long teamId = jsonObject.getLong("teamId");
        common.sendTeamMessage(info,"task",teamId);
        log.info("丹妹温馨提示：任务日志消息为：{}",info);
    }


}
