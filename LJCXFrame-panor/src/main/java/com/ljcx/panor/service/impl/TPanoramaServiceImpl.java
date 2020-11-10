package com.ljcx.panor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.panor.dao.TPanoramaDao;
import com.ljcx.panor.beans.TPanoramaBean;
import com.ljcx.panor.service.TPanoramaService;
import com.ljcx.panor.dto.TPanoramaDto;


@Service
public class TPanoramaServiceImpl extends ServiceImpl<TPanoramaDao, TPanoramaBean> implements TPanoramaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TPanoramaDao tPanoramaDao;

    @Override
    public IPage<TPanoramaBean> pageList(TPanoramaDto tPanoramaDto) {
        IPage<TPanoramaBean> page = new Page<>();
        page.setCurrent(tPanoramaDto.getPageNum());
        page.setSize(tPanoramaDto.getPageSize());
        return tPanoramaDao.pageList(page,tPanoramaDto);
    }

}
