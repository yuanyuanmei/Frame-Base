package com.ljcx.code.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.http.CookieUtils;
import com.ljcx.framework.annotations.LogAnnotation;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.user.dto.AccountDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 登录controller
 */
@Api(value = "用户登录模块")
@Slf4j
@Controller
public class LoginController {

    /**
     * 登录接口
     * @param info
     * info（account 账号,password 密码）
     * @return
     */
    @LogAnnotation("登录")
    @RequestMapping("/login")
    @ResponseBody
    @ValidateCustom(AccountDto.class)
    public ResponseInfo login(@RequestBody String info) {
        AccountDto accountDto = JSONObject.parseObject(info, AccountDto.class);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken(accountDto.getAccount(), accountDto.getPassword(), accountDto.getRememberMe());
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return new ResponseInfo().failed("账号密码不正确");
        }

        // 设置shiro的session过期时间
        subject.getSession().setTimeout(30*60*1000);
        if(accountDto.getRememberMe()){
            CookieUtils.setCookie("REMEMBER_USERNAME", accountDto.getAccount());
        }else{
            CookieUtils.removeCookie("REMEMBER_USERNAME");
        }

        return new ResponseInfo().success("登录成功");
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }



}
