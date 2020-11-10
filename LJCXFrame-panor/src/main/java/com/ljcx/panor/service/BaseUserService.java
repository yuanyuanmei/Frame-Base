package com.ljcx.panor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.panor.beans.BaseUserBean;
import com.ljcx.panor.dto.BaseUserDto;


/**
 * 用户基础表
 *
 * @author dm
 * @date 2020-05-27 10:12:59
 */
public interface BaseUserService extends IService<BaseUserBean> {

    IPage<BaseUserBean> pageList(BaseUserDto baseUserDto);
}

