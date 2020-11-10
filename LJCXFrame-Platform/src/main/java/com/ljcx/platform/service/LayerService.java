package com.ljcx.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.LayerBean;
import com.ljcx.platform.vo.LayerBeanVo;

import java.util.List;
import java.util.Set;


/**
 * 所有图层
 *
 * @author dm
 * @date 2019-12-09 11:42:59
 */
public interface LayerService extends IService<LayerBean> {

    List<LayerBeanVo> listByTeamId(Long teamId);
}

