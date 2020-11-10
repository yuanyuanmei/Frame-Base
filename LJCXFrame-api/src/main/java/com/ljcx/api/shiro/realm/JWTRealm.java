package com.ljcx.api.shiro.realm;

import com.ljcx.api.shiro.jwt.JWTCredentialsMatcher;
import com.ljcx.api.shiro.jwt.JWTToken;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.spring.SpringUtil;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.dao.UserAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * jwt数据源
 * 执行顺序 2
 */
@Slf4j
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public JWTRealm(){
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UserAccountDao accountDao = SpringUtil.getBean(UserAccountDao.class);
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();
        String account = JwtUtils.getAccount(token);
        String username = JwtUtils.getUsername(token);
        if(account == null){
            throw new AuthenticationException("token过期，请重新登录");
        }
        UserAccountBean accountBean = accountDao.findByAccount(account);
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(accountBean, accountBean.getSalt(), "jwtRealm");

        return authorizationInfo;
    }
}
