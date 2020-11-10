package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.FlyAreaBean;
import com.ljcx.api.dto.FlyAreaDto;
import com.ljcx.common.utils.ResponseInfo;

import java.util.List;


/**
 * 飞行区域
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface FlyAreaService extends IService<FlyAreaBean> {

    ResponseInfo save(List<FlyAreaDto> flyAreaDtos);
}

