package com.ljcx.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.LogAnnotation;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.dto.AccountDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录controller
 */
@Api(value = "用户登录模块")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录接口
     * @param info
     * info（account 账号,password 密码）
     * @param response
     * @return
     */
    @LogAnnotation("登录")
    @RequestMapping("/login/login")
    @ValidateCustom(AccountDto.class)
    public ResponseInfo login(@RequestBody String info) {
        AccountDto accountDto = JSONObject.parseObject(info, AccountDto.class);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken(accountDto.getAccount(), accountDto.getPassword());
        try{
            subject.login(token);
            UserAccountBean accountBean = (UserAccountBean) subject.getPrincipal();
            String newToken = JwtUtils.sign(accountBean);
            // 保证账号唯一性
            redisTemplate.opsForValue().set(UserConstants.LOGIN_KEY+accountBean.getUserBaseBean().getUsername(),newToken);
            return new ResponseInfo(newToken);
        }catch (AuthenticationException e){
            return new ResponseInfo().failed(e.getMessage());
        }

    }
    /**
     * 登出
     * @return
     */
    @RequestMapping("/logout")
    public ResponseInfo logout(HttpServletRequest request) {
        UserAccountBean accountBean = (UserAccountBean) SecurityUtils.getSubject().getPrincipal();
        if(accountBean != null && redisTemplate.hasKey(UserConstants.LOGIN_KEY+accountBean.getUserBaseBean().getUsername())){
            String token = redisTemplate.opsForValue().get(UserConstants.LOGIN_KEY + accountBean.getUserBaseBean().getUsername());
            if(token.equals(request.getHeader("token"))){
                redisTemplate.delete(UserConstants.LOGIN_KEY + accountBean.getUserBaseBean().getUsername());
            }
        }
        SecurityUtils.getSubject().logout();
        return new ResponseInfo().success("登出成功");
    }


}
