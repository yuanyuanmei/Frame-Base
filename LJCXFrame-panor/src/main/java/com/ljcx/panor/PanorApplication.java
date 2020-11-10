package com.ljcx.panor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.ljcx.*"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PanorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanorApplication.class, args);
    }

}
