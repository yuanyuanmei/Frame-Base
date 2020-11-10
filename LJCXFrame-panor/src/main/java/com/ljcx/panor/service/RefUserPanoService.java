package com.ljcx.panor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.panor.beans.RefUserPanoBean;
import com.ljcx.panor.dto.RefUserPanoDto;


/**
 * 用户-全景图关联表（收藏）
 *
 * @author dm
 * @date 2020-05-27 10:12:59
 */
public interface RefUserPanoService extends IService<RefUserPanoBean> {

    IPage<RefUserPanoBean> pageList(RefUserPanoDto refUserPanoDto);
}

