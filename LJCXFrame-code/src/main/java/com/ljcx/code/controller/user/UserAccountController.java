package com.ljcx.code.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.code.dto.TeamInfoDto;
import com.ljcx.code.service.TeamInfoService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.user.beans.UserAccountBean;
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

import java.util.Arrays;


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

    @Autowired
    private TeamInfoService teamInfoService;

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
        if(accountDto.getId() == null){
            if(accountDto.getAccount() == null){
                throw new IllegalArgumentException("账号不能为空");
            }
            if(accountDto.getPassword() == null){
                throw new IllegalArgumentException("密码不能为空");
            }
        }
        if(StringUtils.isNotEmpty(accountDto.getAccount()) && accountService.getAccount(accountDto.getAccount()) != null){
            throw new IllegalArgumentException("账号："+accountDto.getAccount() + "已存在");
        }
        ResponseInfo save = accountService.save(accountDto);
        //新增团队关联关系
        if(accountDto.getTeamId() != null && save.getData() != null){
            UserAccountBean accountBean = (UserAccountBean) save.getData();
            TeamInfoDto teamInfoDto = new TeamInfoDto();
            teamInfoDto.setId(accountDto.getTeamId());
            teamInfoDto.setMemberIds(Arrays.asList(accountBean.getUserId()));
            teamInfoService.bindTeam(teamInfoDto);
        }
        return save;
    }



}
