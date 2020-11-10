package com.ljcx.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.beans.LayerBean;
import com.ljcx.code.dto.LayerDto;
import com.ljcx.common.utils.ResponseInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 所有图层
 *
 * @author dm
 * @date 2019-12-09 11:42:59
 */
public interface LayerService extends IService<LayerBean> {

    IPage<LayerBean> pageList(LayerDto layerDto);

}

