package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dto.PanoramaDto;

/**
 * 全景图
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface PanoramaService extends IService<PanoramaBean> {

    IPage<PanoramaBean> pageList(PanoramaDto panoramaDto);

    PanoramaBean save(PanoramaDto panoramaDto);
}

