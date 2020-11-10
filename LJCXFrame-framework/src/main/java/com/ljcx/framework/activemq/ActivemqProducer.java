package com.ljcx.framework.activemq;

import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 消息生产者
 */
@Component
public class ActivemqProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 队列消息
     * @param queueName 队列名称
     * @param content 消息内容
     */
    public void sendQueue(String queueName,String content){
        // 发送消息，destination是发送到的队列，content是待发送的消息
        Destination destination = new ActiveMQQueue(queueName);
        this.jmsMessagingTemplate.convertAndSend(destination, content);
    }

    /**
     * 主题消息
     * @param topicName 主题名称
     * @param content 内容
     */
    public void sendTopic(String topicName,String content){
        Destination destination = new ActiveMQTopic(topicName);
        this.jmsMessagingTemplate.convertAndSend(destination, content);
    }

    /**
     * 延迟发送
     * @param queueName
     * @param content
     * @param time
     */
    public void delaySendQueue(String queueName,String content,long time){
        //获取连接工厂
        ConnectionFactory connectionFactory = this.jmsMessagingTemplate.getConnectionFactory();
        try {
            //获取连接
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //获取session
            Session session = connection.createSession();
            //创建一个消息队列
            Destination destination = session.createQueue(queueName);
            //创建生产者
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //包装消息
            TextMessage message = session.createTextMessage(content);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,time);
            //发送消息
            producer.send(message);
            //提交事务
            session.commit();
            //关闭资源
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }




}
