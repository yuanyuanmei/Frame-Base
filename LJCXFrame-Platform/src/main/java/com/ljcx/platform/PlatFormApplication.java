package com.ljcx.platform;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.naming.Context;

/**
 * 配置了自定义数据源，一定要排除自动注入数据源，否则会导致相互引用问题！！！
 */
@ComponentScan({"com.ljcx.*"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

public class PlatFormApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatFormApplication.class, args);
    }
}
