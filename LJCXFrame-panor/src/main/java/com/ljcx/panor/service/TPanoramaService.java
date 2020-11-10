package com.ljcx.panor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.panor.beans.TPanoramaBean;
import com.ljcx.panor.dto.TPanoramaDto;


/**
 * 全景图表
 *
 * @author dm
 * @date 2020-05-27 10:12:59
 */
public interface TPanoramaService extends IService<TPanoramaBean> {

    IPage<TPanoramaBean> pageList(TPanoramaDto tPanoramaDto);
}

