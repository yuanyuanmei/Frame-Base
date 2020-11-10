package com.ljcx.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.LayerDao;
import com.ljcx.code.beans.LayerBean;
import com.ljcx.code.service.LayerService;
import com.ljcx.code.dto.LayerDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class LayerServiceImpl extends ServiceImpl<LayerDao, LayerBean> implements LayerService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private LayerDao layerDao;

    @Autowired
    private SysFileDao fileDao;

    @Autowired
    private SysFileService fileService;

    @Override
    public IPage<LayerBean> pageList(LayerDto layerDto) {
        IPage<LayerBean> page = new Page<>();
        page.setCurrent(layerDto.getPageNum());
        page.setSize(layerDto.getPageSize());
        return layerDao.pageList(page,layerDto, UserUtil.getCurrentUser());
    }



}
