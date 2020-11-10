package com.ljcx.code.service.impl;

import com.ljcx.code.beans.FlyAreaBean;
import com.ljcx.code.dto.FlyAreaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.FlyAreaDao;
import com.ljcx.code.service.FlyAreaService;

@Service
public class FlyAreaServiceImpl extends ServiceImpl<FlyAreaDao, FlyAreaBean> implements FlyAreaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private FlyAreaDao flyAreaDao;

    @Override
    public IPage<FlyAreaBean> pageList(FlyAreaDto flyAreaDto) {
        IPage<FlyAreaBean> page = new Page<>();
        page.setCurrent(flyAreaDto.getPageNum());
        page.setSize(flyAreaDto.getPageSize());
        return flyAreaDao.pageList(page,flyAreaDto);
    }

}
