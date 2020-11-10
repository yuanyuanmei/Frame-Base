package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.CarInfoBean;
import com.ljcx.api.dto.CarInfoDto;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 设备车信息表
 *
 * @author dm
 * @date 2019-11-14 15:55:00
 */
public interface CarInfoService extends IService<CarInfoBean> {

    IPage<CarInfoBean> pageList(CarInfoDto carInfoDto);

    ResponseInfo saveCar(CarInfoDto carInfoDto);
}

