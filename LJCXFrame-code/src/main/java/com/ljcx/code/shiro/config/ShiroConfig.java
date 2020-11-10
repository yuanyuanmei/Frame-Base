package com.ljcx.code.shiro.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ljcx.code.shiro.filter.OnlineSessionFilter;
import com.ljcx.code.shiro.realm.UserRealm;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.user.constants.UserConstants;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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
		chainDefinition.addPathDefinition("/login", "anon");
		chainDefinition.addPathDefinition("/static/**", "anon");
		chainDefinition.addPathDefinition("/login.html", "anon");
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
		filterMap.put("dm",onlineSessionFilter());
		factoryBean.setFilters(filterMap);
		factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
		return factoryBean;
	}

	/**
	 * 自定义在线用户处理过滤器
	 */
	public OnlineSessionFilter onlineSessionFilter()
	{
		return new OnlineSessionFilter(userUtil);
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
	public SecurityManager securityManager(UserUtil userUtil) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealms(Arrays.asList(userRealm(userUtil)));
		return securityManager;
	}

	/**
	 * 认证策略
	 */
	@Bean
	public Authenticator authenticator(UserUtil userUtil){
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setRealms(Arrays.asList(userRealm(userUtil)));
		authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		return authenticator;
	}

	@Bean("userRealm")
	public Realm userRealm(UserUtil userUtil) {
		UserRealm myShiroRealm = new UserRealm(userUtil);
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
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
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * thymeleaf模板引擎和shiro框架的整合
	 */
	@Bean
	public ShiroDialect shiroDialect()
	{
		return new ShiroDialect();
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