package com.ljcx.generator.config;

import com.ljcx.generator.dao.GeneratorDao;
import com.ljcx.generator.dao.MySQLGeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DBConfig {

    @Value("${ljcx.database: mysql}")
    private String database;

    @Autowired
    private MySQLGeneratorDao mySQLGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao(){
        if("mysql".equalsIgnoreCase(database)){
            return mySQLGeneratorDao;
        }else{
            throw new RuntimeException("不支持当前数据库"+database);
        }
    }


}
