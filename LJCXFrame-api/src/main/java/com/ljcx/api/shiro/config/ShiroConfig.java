package com.ljcx.api.shiro.config;


import com.ljcx.api.shiro.filter.JWTFilter;
import com.ljcx.api.shiro.realm.JWTRealm;
import com.ljcx.api.shiro.realm.UserRealm;
import com.ljcx.api.shiro.util.UserUtil;
import com.ljcx.user.constants.UserConstants;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

/**
 * shiro配置
 *
 */
@Configuration
public class ShiroConfig {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private UserUtil userUtil;

	/**
	 * mapperScan映射器
	 * @return
	 */
	@Bean
	protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinition("/css/**", "anon");
		chainDefinition.addPathDefinition("/fonts/**", "anon");
		chainDefinition.addPathDefinition("/img/**", "anon");
		chainDefinition.addPathDefinition("/js/**", "anon");
		chainDefinition.addPathDefinition("/files/*", "anon");
		chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
		chainDefinition.addPathDefinition("/logout", "anon");
		chainDefinition.addPathDefinition("/login/login", "anon");
		chainDefinition.addPathDefinition("/**.html", "anon");
		chainDefinition.addPathDefinition("/static/**", "anon");
		chainDefinition.addPathDefinition("/login.html", "anon");
		chainDefinition.addPathDefinition("/test.html", "anon");
		chainDefinition.addPathDefinition("/websocket/**", "anon"); //避免拦截/websocket链接
		chainDefinition.addPathDefinition("/profile/upload/**", "anon");
		chainDefinition.addPathDefinition("/**", "dm");
		return chainDefinition;
	}


	/**
	 * shiro过滤器
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filterMap = factoryBean.getFilters();
		filterMap.put("dm",jwtFilter(redisTemplate));
		factoryBean.setFilters(filterMap);
		factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
		return factoryBean;
	}


	//@Bean
	protected JWTFilter jwtFilter(RedisTemplate redisTemplate){
		JWTFilter jwtFilter = new JWTFilter(redisTemplate);
		jwtFilter.setLoginUrl("/logout");
		return jwtFilter;
	}


	/**
	 * 注册过滤器
	 */
	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager) throws Exception{
		FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
		filterRegistration.setFilter((Filter)shiroFilter(securityManager).getObject());
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setAsyncSupported(true);
		filterRegistration.setEnabled(true);
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ASYNC);
		return filterRegistration;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setAuthenticator(authenticator());
		securityManager.setAuthorizer(authorizer());
		return securityManager;
	}


	/**
	 * 认证策略
	 */
	@Bean
	public Authenticator authenticator(){
		MultiRealmAuthenticator authenticator = new MultiRealmAuthenticator();
		authenticator.setRealms(Arrays.asList(jwtRealm(),userRealm()));
		authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		return authenticator;
	}

	/**
	 * 初始化authorizer 认证器 权限认证
	 * @return
	 */
	@Bean
	public Authorizer authorizer() {
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();//这里的
		authorizer.setRealms(Arrays.asList(jwtRealm(),userRealm()));
		return authorizer;
	}

	@Bean("userRealm")
	public Realm userRealm() {
		UserRealm myShiroRealm = new UserRealm(userUtil);
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean("jwtRealm")
	public Realm jwtRealm(){
		JWTRealm jwtRealm = new JWTRealm();
		return jwtRealm;
	}

	/**
	 * 凭证匹配器
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(UserConstants.HASH_ITERATIONS);

		return hashedCredentialsMatcher;
	}

	/**
	 * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
	 * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
	 */
	@Bean
	protected SessionStorageEvaluator sessionStorageEvaluator(){
		DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);
		return sessionStorageEvaluator;
	}

	/**
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
	 * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}