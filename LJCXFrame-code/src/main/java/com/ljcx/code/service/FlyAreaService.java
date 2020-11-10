package com.ljcx.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.code.beans.FlyAreaBean;
import com.ljcx.code.dto.FlyAreaDto;


/**
 * 飞行区域
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface FlyAreaService extends IService<FlyAreaBean> {

    IPage<FlyAreaBean> pageList(FlyAreaDto flyAreaDto);
}

