package com.ljcx.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.async.AsyncService;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dao.PanoramaDao;
import com.ljcx.api.dto.PanoramaDto;
import com.ljcx.api.service.PanoramaService;
import com.ljcx.common.constant.UrlConstant;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.live.common.profile.HttpProfile;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.IGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@Slf4j
public class PanoramaServiceImpl extends ServiceImpl<PanoramaDao, PanoramaBean> implements PanoramaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private PanoramaDao panoramaDao;

    @Autowired
    private SysFileDao fileDao;

    @Override
    public IPage<PanoramaBean> pageList(PanoramaDto panoramaDto) {
        IPage<PanoramaBean> page = new Page<>();
        page.setCurrent(panoramaDto.getPageNum());
        page.setSize(panoramaDto.getPageSize());
        return panoramaDao.pageList(page,panoramaDto);
    }

    @Override
    @Transactional
    public PanoramaBean save(PanoramaDto panoramaDto) {
        PanoramaBean panoramaBean = generator.convert(panoramaDto, PanoramaBean.class);
        if(panoramaDto.getFileId() != null){
            SysFileBean fileBean = fileDao.selectById(panoramaDto.getFileId());
            panoramaBean.setFilePath(fileBean.getFilePath());
        }
        saveOrUpdate(panoramaBean);
        return panoramaBean;
    }


}
