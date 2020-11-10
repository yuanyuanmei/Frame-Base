package com.ljcx.api.shiro.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UserUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public UserBaseBean getCurrentUser() {
        UserBaseBean user = JSONObject.parseObject(redisTemplate.opsForValue().get(UserConstants.SHIRO_SESSION+SecurityUtils.getSubject().getSession().getId()),UserBaseBean.class);
        return user;
    }

    public void setUser(UserBaseBean baseBean){
        // 保存个人信息
        redisTemplate.opsForValue().set(UserConstants.SHIRO_SESSION+SecurityUtils.getSubject().getSession().getId(), JSON.toJSONString(baseBean),3600, TimeUnit.SECONDS);
        // 保存登录信息
        redisTemplate.opsForValue().set(UserConstants.LOGIN_KEY+baseBean.getUsername(),SecurityUtils.getSubject().getSession().getId()+"");
    }

    @SuppressWarnings("unchecked")
    public List<SysPermissionBean> getCurrentPermissions(String username) {
        List<SysPermissionBean> list = JSONArray.parseArray(redisTemplate.opsForValue().get(UserConstants.USER_PERMISSIONS+username),SysPermissionBean.class);
        return list;
    }

    public void setPermissions(String username, List<SysPermissionBean> permissions){
        redisTemplate.opsForValue().set(UserConstants.USER_PERMISSIONS+username, JSON.toJSONString(permissions),3600, TimeUnit.SECONDS);
    }

    public void delCurrentUser(){
        if(redisTemplate.hasKey(UserConstants.SHIRO_SESSION+SecurityUtils.getSubject().getSession().getId())){
            redisTemplate.delete(UserConstants.SHIRO_SESSION+SecurityUtils.getSubject().getSession().getId());
        }
    }

    public void delPermissions(){
        if(getCurrentUser() != null && redisTemplate.hasKey(UserConstants.USER_PERMISSIONS+getCurrentUser().getUsername())){
            redisTemplate.delete(UserConstants.USER_PERMISSIONS+getCurrentUser().getUsername());
        }
    }

    public boolean getLoginState(String username, Serializable key){
        if(redisTemplate.hasKey(UserConstants.LOGIN_KEY+username)){
            return redisTemplate.opsForValue().get(UserConstants.LOGIN_KEY+username).equals(key+"");
        }else{
            return false;
        }
    }



}
