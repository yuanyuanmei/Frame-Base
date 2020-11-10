package com.ljcx.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.FlyAreaBean;
import com.ljcx.platform.dto.FlyAreaDto;
import com.ljcx.platform.vo.FlyAreaVo;

import java.util.List;


/**
 * 飞行区域
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface FlyAreaService extends IService<FlyAreaBean> {

    List<FlyAreaVo> getListByCategory(FlyAreaDto flyAreaDto);

}

