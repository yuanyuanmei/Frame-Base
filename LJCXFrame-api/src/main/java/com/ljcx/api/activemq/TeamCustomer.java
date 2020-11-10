package com.ljcx.api.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.dao.UavInfoDao;
import com.ljcx.api.dto.LiveDto;
import com.ljcx.api.dto.UavInfoDto;
import com.ljcx.api.mgbean.UavInfoMongo;
import com.ljcx.api.vo.UavInfoVo;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.common.utils.ReflectUtil;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.netty.ApplicationWebSocket;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.service.UserBaseService;
import com.ljcx.user.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
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
public class TeamCustomer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CommonCustomer common;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IGenerator generator;

    /**
     *  监听无人机状态的主题
     */
    @JmsListener(destination = "topic_uav_state",containerFactory = "jmsListenerContainerTopic")
    public void receiveUavStateTopic(Message message) throws IOException {

        Object info = common.getInfo(message);
        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        Long id = jsonObject.getLong("id") ;
        Long teamId = jsonObject.getLong("teamId");
        //把飞机对象存入redis
        if(id != null && id != 0l){
            redisTemplate.opsForValue().set(RedisConstant.UAV_STATE_+teamId+"_"+id, info.toString(),5*60, TimeUnit.SECONDS);
            //将对象添加进集合里
            redisTemplate.opsForZSet().add(RedisConstant.UAV_ONLINE_NUMS_ + teamId,RedisConstant.UAV_STATE_+id,System.currentTimeMillis());
            //将对象存入mongodb
            UavInfoVo uavInfoVo = generator.convert(jsonObject, UavInfoVo.class);
            log.info("==============================>,{}",uavInfoVo);
            UavInfoMongo mongo = generator.convert(uavInfoVo, UavInfoMongo.class);
            mongo.setUavId(uavInfoVo.getId());
            mongo.setLocalTimeStamp(System.currentTimeMillis());
            log.info("==============================>,{}",mongo);
            mongoTemplate.save(mongo);
        }

        common.sendTeamMessage(info,"plane",teamId);
        log.info("丹妹温馨提示：飞机状态消息为：{}",info);

    }

    /**
     *  监听人员状态的主题
     */
    @JmsListener(destination = "topic_member_state",containerFactory = "jmsListenerContainerTopic")
    public void receiveMemberStateTopic(Message message) throws IOException {

        Object info = common.getInfo(message);

        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        Long id = jsonObject.getLong("id") ;
        Long teamId = jsonObject.getLong("teamId");
        common.sendTeamMessage(info,"man",teamId);
        //把人员对象存入redis
        if(id != null && id != 0l){
            redisTemplate.opsForValue().set(RedisConstant.MEMBER_STATE_+teamId+"_"+id,info.toString(),5*60, TimeUnit.SECONDS);
            //将对象添加进集合里
            redisTemplate.opsForZSet().add(RedisConstant.MEMBER_ONLINE_NUMS_ + teamId,RedisConstant.MEMBER_STATE_+id,System.currentTimeMillis());
        }

        log.info("丹妹温馨提示：人员状态消息为：{}",info.toString());

    }

    /**
     *  监听人员状态的主题
     */
    @JmsListener(destination = "topic_car_state",containerFactory = "jmsListenerContainerTopic")
    public void receiveCarStateTopic(Message message) throws IOException {

        Object info = common.getInfo(message);

        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        Long id = jsonObject.getLong("id") ;
        Long teamId = jsonObject.getLong("teamId");
        common.sendTeamMessage(info,"car",teamId);
        //把人员对象存入redis
        if(id != null && id != 0l){
            redisTemplate.opsForValue().set(RedisConstant.CAR_STATE_+teamId+"_"+id,info.toString(),5*60, TimeUnit.SECONDS);
            //将对象添加进集合里
            redisTemplate.opsForZSet().add(RedisConstant.CAR_ONLINE_NUMS_ + teamId,RedisConstant.CAR_STATE_+id,System.currentTimeMillis());
        }

        log.info("丹妹温馨提示：汽车状态消息为：{}",info.toString());

    }

    /**
     *  监听关闭设备状态的主题
     */
    @JmsListener(destination = "topic_team_close",containerFactory = "jmsListenerContainerTopic")
    public void receiveCloseTopic(Message message) throws IOException {

        Object info = common.getInfo(message);

        JSONObject jsonObject = JSONObject.parseObject(info.toString());
        Long id = jsonObject.getLong("id") ;
        Long teamId = jsonObject.getLong("teamId");
        Integer type = jsonObject.getInteger("type");
        String messageType = "";
        if(type == 1){
            messageType = "plane";
            redisTemplate.delete(RedisConstant.UAV_STATE_+teamId+"_"+id);
            redisTemplate.opsForZSet().remove(RedisConstant.UAV_ONLINE_NUMS_+teamId, RedisConstant.UAV_STATE_+id);
        }else if(type == 2){
            messageType = "car";
            redisTemplate.delete(RedisConstant.CAR_STATE_+teamId+"_"+id);
            redisTemplate.opsForZSet().remove(RedisConstant.CAR_ONLINE_NUMS_+teamId, RedisConstant.CAR_STATE_+id);
        }else if(type == 3){
            messageType = "man";
            redisTemplate.delete(RedisConstant.MEMBER_STATE_+teamId+"_"+id);
            redisTemplate.opsForZSet().remove(RedisConstant.MEMBER_ONLINE_NUMS_+teamId, RedisConstant.MEMBER_STATE_+id);
        }
        common.sendTeamMessage(info,messageType,teamId);
        log.info("丹妹温馨提示：关闭设备消息为：{}",info.toString());

    }


}
