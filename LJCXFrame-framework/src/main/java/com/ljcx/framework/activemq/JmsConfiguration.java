package com.ljcx.framework.activemq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.ConnectionFactory;

/**
 * JMS消息基本组件
 *
 * 1. ConnectionFactory(连接工厂)
 * 创建Connection对象的工厂，针对两种不同的Jms消息模型，分别有QueueConnectionFactory
 * 和TopicConnectionFactory两种,可以通过JNDI来查找ConnectionFactory对象
 *
 * 2. Destination(session创建出的消息队列或主题)
 * Destination的意思是消息生产者的消息发送目标或者说消息消费者的消息来源。对于消息生产者来说，它的
 * Destination是某个队列（Queue）或某个主题（Topic）;对于消息消费者来说，它的Destination也是某个队列
 * 或主题（即消息来源）
 * 所以，Destination实际上就是两种类型的对象：queue,topic可以通过JNDI来查找Destination.
 *
 * 3. Connection(连接工厂创建出的连接)
 * Connection表示在客户端和JMS系统之间建立的连接（对TCP/IP socket的包装）。Connection可以产生一个或
 * 多个session。和ConnectionFactory一样，connection也有两种类型：queueConnection和topicConnection
 *
 * 4.session(连接创建的session)
 * Session是我们操作消息的接口。可以通过session创建生产者，消费者，消息等。session提供了事务的功能。当我
 * 们需要使用session发送/接收多个消息时，可以将这些发送/接收动作放到一个事务中，同样，也分QueueSession和
 * TopicSession.
 *
 * 5.消息的生产者
 * 消息生产者由session创建，并用于将消息发送到Destination.同样，消息生产者分两种类型：
 * QueueSender和TopicPublisher.可以调用消息生产者的方法（send或publish方法）发送消息。
 *
 * 6.消息消费者
 * 消息消费者由session创建，用于接收呗发送到destination的消息。两种类型：queueReceiver和
 * topicSubscriber.可分别通过session的createReceiver(queue)或createSubscriber(topic)来
 * 创建。当然，也可以session的createDurableSubsriber方法来创建持久化的订阅者。
 *
 * 7.MessageListener
 * 消息监听器。如果注册了消息监听器，一旦消息到达，将自动调用监听器的onMessage方法。EJB中的MDB
 * （Message-Driven Bean）就是一种MessageListenner
 *
 *                                  connectionFactory
 *                                         1
 *                                     connection
 *                                         1
 *                                      session
 *                                         1
 *                          1              1           1
 *                   MessageProducer Destination   TextMessage
 *
 * 实现MQ延迟
 *
 */
@Configuration
@EnableJms
public class JmsConfiguration {
    // topic模式下的ListenerContainer
    @Bean("jmsListenerContainerTopic")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory)
    {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        FixedBackOff backOff = new FixedBackOff();
        backOff.setMaxAttempts(3);
        bean.setBackOff(backOff);
        return bean;
    }

    // queue模式的ListenerContainer
    @Bean("jmsListenerContainerQueue")
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(activeMQConnectionFactory);
        FixedBackOff backOff = new FixedBackOff();
        backOff.setMaxAttempts(3);
        bean.setBackOff(backOff);
        return bean;
    }
}


