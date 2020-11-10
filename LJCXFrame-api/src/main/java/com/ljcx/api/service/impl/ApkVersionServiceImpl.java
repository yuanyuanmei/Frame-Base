package com.ljcx.api.service.impl;

import com.ljcx.api.vo.ApkVersionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.api.dao.ApkVersionDao;
import com.ljcx.api.beans.ApkVersionBean;
import com.ljcx.api.service.ApkVersionService;
import com.ljcx.api.dto.ApkVersionDto;


@Service
public class ApkVersionServiceImpl extends ServiceImpl<ApkVersionDao, ApkVersionBean> implements ApkVersionService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private ApkVersionDao apkVersionDao;


    @Override
    public ApkVersionVo info() {
        return apkVersionDao.info();
    }
}
