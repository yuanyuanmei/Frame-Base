package com.ljcx.panor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.panor.dao.BaseUserDao;
import com.ljcx.panor.beans.BaseUserBean;
import com.ljcx.panor.service.BaseUserService;
import com.ljcx.panor.dto.BaseUserDto;


@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUserBean> implements BaseUserService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private BaseUserDao baseUserDao;

    @Override
    public IPage<BaseUserBean> pageList(BaseUserDto baseUserDto) {
        IPage<BaseUserBean> page = new Page<>();
        page.setCurrent(baseUserDto.getPageNum());
        page.setSize(baseUserDto.getPageSize());
        return baseUserDao.pageList(page,baseUserDto);
    }

}
