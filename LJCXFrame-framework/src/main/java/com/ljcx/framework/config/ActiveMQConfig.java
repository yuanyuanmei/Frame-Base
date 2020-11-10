package com.ljcx.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * activemq 配置信息
 */
@Component
@PropertySource("classpath:config.properties")
public class ActiveMQConfig {
    /**
     * 用户名
     */
    public static String MQUSERNAME;
    /**
     * 密码
     */
    public static String MQPWD;
    /**
     * 地址
     */
    public static String MQURL;
    /**
     * 分发策略主题
     */
    public static String PTP;

    @Value("${mq_username}")
    public void setMQUSERNAME(String MQUSERNAME) {
        this.MQUSERNAME = MQUSERNAME;
    }

    @Value("${mq_pwd}")
    public static void setMQPWD(String MQPWD) {
        ActiveMQConfig.MQPWD = MQPWD;
    }

    @Value("${mq_url}")
    public void setMQURL(String MQURL) {
        this.MQURL = MQURL;
    }

    @Value("${topicPTP}")
    public static void setPTP(String PTP) {
        ActiveMQConfig.PTP = PTP;
    }
}
