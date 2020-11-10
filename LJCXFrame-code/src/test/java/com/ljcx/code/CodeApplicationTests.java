package com.ljcx.code;

import com.ljcx.user.dao.UserAccountDao;
import com.ljcx.user.service.SysRoleService;
import com.ljcx.user.service.UserAccountService;
import com.ljcx.user.service.UserBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CodeApplicationTests {

    @Autowired
    private UserBaseService baseService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private UserAccountDao accountDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForZSet().add("onlineUsers","1",System.currentTimeMillis());
        redisTemplate.opsForZSet().add("onlineUsers","2",System.currentTimeMillis());
        redisTemplate.opsForZSet().add("onlineUsers","3",System.currentTimeMillis()+1*30*1000);
        redisTemplate.opsForZSet().remove("onlineUsers","1");
        System.out.println(redisTemplate.opsForZSet().zCard("onlineUsers"));
        System.out.println(redisTemplate.opsForZSet().rangeByScore("onlineUsers",System.currentTimeMillis(),System.currentTimeMillis()+5*30*1000));
        System.out.println(redisTemplate.opsForZSet().count("onlineUsers",System.currentTimeMillis(),System.currentTimeMillis()+5*30*1000));
    }


}
