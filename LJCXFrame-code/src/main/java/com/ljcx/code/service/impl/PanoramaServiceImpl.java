package com.ljcx.code.service.impl;

import com.ljcx.code.beans.PanoramaBean;
import com.ljcx.code.dto.PanoramaDto;
import com.ljcx.code.shiro.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.PanoramaDao;
import com.ljcx.code.service.PanoramaService;


@Service
public class PanoramaServiceImpl extends ServiceImpl<PanoramaDao, PanoramaBean> implements PanoramaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private PanoramaDao panoramaDao;

    @Override
    public IPage<PanoramaBean> pageList(PanoramaDto panoramaDto) {
        IPage<PanoramaBean> page = new Page<>();
        page.setCurrent(panoramaDto.getPageNum());
        page.setSize(panoramaDto.getPageSize());
        return panoramaDao.pageList(page,panoramaDto, UserUtil.getCurrentUser());
    }

}
