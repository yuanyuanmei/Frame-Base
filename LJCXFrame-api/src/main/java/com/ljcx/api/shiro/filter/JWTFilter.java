package com.ljcx.api.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.shiro.jwt.JWTToken;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT拦截器
 * 执行顺序 1-4
 */
@Slf4j
public class JWTFilter extends AuthenticatingFilter {

    private static final int tokenRefreshInterval = 300;

    private RedisTemplate<String,String> redisTemplate;


    public JWTFilter(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception{
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //对于OPTION请求做拦截
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response){
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
        request.setAttribute("jwtShiroFilter.FILTERED", true);
    }

    /**
     *  * 首先我们先从入口的Filter开始。从AuthenticatingFilter继承，重写isAccessAllow方法，方法中调用父类executeLogin()。
     *  * 父类的这个方法首先会createToken()，然后调用shiro的Subject.login()方法。
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){

        if(this.isLoginRequest(request, response)){
            return true;
        }
        Boolean afterFiltered = (Boolean) request.getAttribute("jwtShiroFilter.FILTERED");
        if(BooleanUtils.isTrue(afterFiltered)){
            return true;
        }

        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e){
            log.error("Not found any token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }

        /**
         * 判断Token是否被挤下去
         */
        if(allowed){
            try {
                JWTToken token = (JWTToken) createToken(request, response);
                String username = JwtUtils.getUsername(token.getToken());
                if(redisTemplate.hasKey(UserConstants.LOGIN_KEY+username)){
                    boolean flag = redisTemplate.opsForValue().get(UserConstants.LOGIN_KEY+username).equals(token.getToken());
                    if(!flag){
                        request.setAttribute("token_error","kickout");
                        return flag;
                    }
                }else{
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return allowed || super.isPermissive(mappedValue);


    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String jwtToken = getToken(servletRequest);
        if(StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)){
            return new JWTToken(jwtToken);
        }
        return null;
    }

    /**
     * 从请求头部或参数中获取token
     */
    private String getToken(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String loginToken = httpRequest.getParameter(UserConstants.TOKEN);
        if (StringUtils.isBlank(loginToken)) {
            loginToken = httpRequest.getHeader(UserConstants.TOKEN);
        }
        return StringUtils.removeStart(loginToken,"Bearer ");
    }

    /**
     * 访问被拒绝
     * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        String token_error = String.valueOf(request.getAttribute("token_error"));
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        if(token_error.equals("kickout")){
            httpResponse.getWriter().write(OTHER_LOGIN);
            httpResponse.setStatus(307);
        }else{
            httpResponse.getWriter().write(ERR_INFO);
            httpResponse.setStatus(308);
        }
        fillCorsHeader(WebUtils.toHttp(request), httpResponse);
        return false;
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if(token instanceof JWTToken){
            JWTToken jwtToken = (JWTToken) token;
            UserAccountBean accountBean = (UserAccountBean) subject.getPrincipal();
            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
            if(shouldRefresh){
                newToken = JwtUtils.sign(accountBean.getUserBaseBean().getId()
                        ,accountBean.getUserBaseBean().getUsername()
                        ,accountBean.getAccount()
                        ,accountBean.getSalt()
                        ,3600);
            }
        }

        if(StringUtils.isNotBlank(newToken)){
            httpResponse.setHeader(UserConstants.TOKEN, newToken);
        }

        return true;

    }
    

    private boolean shouldTokenRefresh(Date issuedAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issuedAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }


    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }

    private static String OTHER_LOGIN = JSONObject
            .toJSONString(new ResponseInfo(307, "该用户已在别的地方登录"));
    private static String ERR_INFO = JSONObject
            .toJSONString(new ResponseInfo(308, "token不存在或者过期"));

}
