package com.ljcx.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.platform.beans.CarInfoBean;
import com.ljcx.platform.dao.CarInfoDao;
import com.ljcx.platform.dto.CarInfoDto;
import com.ljcx.platform.service.CarInfoService;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("carInfoService")
public class CarInfoServiceImpl extends ServiceImpl<CarInfoDao, CarInfoBean> implements CarInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private CarInfoDao carInfoDao;

    @Override
    public IPage<CarInfoBean> pageList(CarInfoDto carInfoDto) {
        IPage<CarInfoBean> page = new Page<>();
        page.setCurrent(carInfoDto.getPageNum());
        page.setSize(carInfoDto.getPageSize());
        return carInfoDao.pageList(page,carInfoDto);
    }

}
