package com.ljcx.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.dto.AccountDto;
import com.ljcx.user.service.UserAccountService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 账号controller
 */
@Api(value = "用户账号模块")
@RestController
@RequestMapping("/account")
@Slf4j
public class UserAccountController {

    @Autowired
    private UserAccountService accountService;

    /**
     * 判断用户名是否存在
     * @param info
     * info (account 用户名)
     * @return
     */
    @PostMapping("/isExist")
    public ResponseInfo isExist(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(accountService.getAccount(jsonObject.getString("account")) != null){
            throw new IllegalArgumentException("账号："+jsonObject.getString("account") + "已存在");
        }
        return new ResponseInfo().success("可以注册");
    }

    /**
     * 注册账号
     * @param info
     * info (account 对象)
     * @return
     */
    @PostMapping("/save")
    @ValidateCustom(AccountDto.class)
    public ResponseInfo register(@RequestBody String info) {
        AccountDto accountDto = JSONObject.parseObject(info, AccountDto.class);
        if(StringUtils.isNotEmpty(accountDto.getAccount()) && accountService.getAccount(accountDto.getAccount()) != null){
            throw new IllegalArgumentException("账号："+accountDto.getAccount() + "已存在");
        }
        return accountService.save(accountDto);
    }
    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    //@RequiresPermissions("sys:user:update")
    public ResponseInfo updatePwd(@RequestBody String info, HttpServletRequest request) {
        return new ResponseInfo(accountService.updatePwd(info, JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN))));
    }


}
