package com.ljcx.panor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.panor.dao.RefUserPanoDao;
import com.ljcx.panor.beans.RefUserPanoBean;
import com.ljcx.panor.service.RefUserPanoService;
import com.ljcx.panor.dto.RefUserPanoDto;


@Service
public class RefUserPanoServiceImpl extends ServiceImpl<RefUserPanoDao, RefUserPanoBean> implements RefUserPanoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private RefUserPanoDao refUserPanoDao;

    @Override
    public IPage<RefUserPanoBean> pageList(RefUserPanoDto refUserPanoDto) {
        IPage<RefUserPanoBean> page = new Page<>();
        page.setCurrent(refUserPanoDto.getPageNum());
        page.setSize(refUserPanoDto.getPageSize());
        return refUserPanoDao.pageList(page,refUserPanoDto);
    }

}
