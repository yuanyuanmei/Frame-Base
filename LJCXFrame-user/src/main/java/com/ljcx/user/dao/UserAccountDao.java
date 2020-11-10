package com.ljcx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljcx.user.beans.UserAccountBean;

import java.util.List;

public interface UserAccountDao extends BaseMapper<UserAccountBean> {

    /**
     * 查询账号是否存在
     * @param account
     * @return
     */
    UserAccountBean findByAccount(String account);

    /**
     * 账号分页列表
     * @param page
     * @return
     */
    IPage<UserAccountBean> list(Page page);

}