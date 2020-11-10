package com.ljcx.user.constants;

/**
 * 用户相关常量
 *
 * @author 小威老师 xiaoweijiagou@163.com
 *
 */
public interface UserConstants {

	/** 加密次数 */
	int HASH_ITERATIONS = 3;

	String USER_INFO = "user_info_";

	String USER_PERMISSIONS = "user_permissions_";

	/** 登陆token(nginx中默认header无视下划线) */
	String LOGIN_TOKEN = "shiro:token:";

	String SHIRO_SESSION = "shiro:session:";

	String LOGIN_KEY = "login_key_";

	String LOGIN_USER = "login_user";

	String TOKEN = "token";

	// APP登录权限
	String APP_LOGIN = "APP_LOGIN";

	//后台管理员权限
	String ADMIN_LOGIN = "ADMIN_LOGIN";

	//平台权限
	String INDEX_LOGIN = "INDEX_LOGIN";
}
