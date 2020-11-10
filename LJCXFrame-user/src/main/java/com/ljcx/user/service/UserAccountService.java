package com.ljcx.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.dto.AccountDto;

public interface UserAccountService extends IService<UserAccountBean> {

    //密码加密
    String passwordEncoder(String credentials, String salt);

    //根据账号查找用户
    UserAccountBean getAccount(String account);

    //更新账号
    ResponseInfo save(AccountDto accountDto);

    IPage<UserAccountBean> list(Page<UserAccountBean> page);

    String generateJwtToken(String username);

    ResponseInfo updatePwd(String info,Long userId);
}
