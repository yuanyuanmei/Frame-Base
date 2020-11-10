package com.ljcx.code.shiro.util;

import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class UserUtil {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public static UserBaseBean getCurrentUser() {
		UserBaseBean user = (UserBaseBean) getSession().getAttribute(UserConstants.LOGIN_USER);
		return user;
	}

	public void setUser(UserBaseBean baseBean){
		// 保存个人信息
		getSession().setAttribute(UserConstants.LOGIN_USER, baseBean);
		// 保存登录信息
		redisTemplate.opsForValue().set(UserConstants.LOGIN_KEY+baseBean.getUsername(),SecurityUtils.getSubject().getSession().getId()+"");
	}

	@SuppressWarnings("unchecked")
	public static List<SysPermissionBean> getCurrentPermissions() {
		List<SysPermissionBean> list = (List<SysPermissionBean>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);
		return list;
	}

	public static void setPermissions(List<SysPermissionBean> permissions){
		getSession().setAttribute(UserConstants.USER_PERMISSIONS, permissions);
	}

	public  boolean getLoginState(String username, Serializable key){
		if(redisTemplate.hasKey(UserConstants.LOGIN_KEY+username)){
			return redisTemplate.opsForValue().get(UserConstants.LOGIN_KEY+username).equals(key+"");
		}
		return false;
	}

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session;
	}



}
