package com.ljcx.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.gaode.utils.GaodeUtils;
import com.ljcx.common.utils.IPUtils;
import com.ljcx.common.utils.MapUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.common.utils.http.CookieUtils;
import com.ljcx.framework.annotations.LogAnnotation;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.user.dto.AccountDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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
    @RequestMapping("/login/login")
    @ResponseBody
    @ValidateCustom(AccountDto.class)
    public ResponseInfo login(@RequestBody String info, HttpServletRequest request) {
        AccountDto accountDto = JSONObject.parseObject(info, AccountDto.class);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken(accountDto.getAccount(), accountDto.getPassword(), accountDto.getRememberMe());
        try{
            subject.login(token);
            String ipAddr = IPUtils.getIpAddr(request);
            JSONObject ipResp = GaodeUtils.getIPAddr(ipAddr);
            String rectangle = ipResp.getString("rectangle");
            if(StringUtils.isNotEmpty(rectangle)){
                String location = rectangle.split(";")[0];
                UserUtil.getCurrentUser().setLng(location.split(",")[0]);
                UserUtil.getCurrentUser().setLat(location.split(",")[1]);
            }
        }catch (UnknownAccountException e){
            return new ResponseInfo().failed(e.getMessage());
        }catch (IncorrectCredentialsException e){
            return new ResponseInfo().failed(e.getMessage());
        }catch (AuthenticationException e){
            return new ResponseInfo().failed("系统原因，登录失败");
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
