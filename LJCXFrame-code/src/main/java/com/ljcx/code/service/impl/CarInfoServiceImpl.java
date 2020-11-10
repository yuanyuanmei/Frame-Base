package com.ljcx.code.service.impl;

import com.ljcx.code.beans.CarInfoBean;
import com.ljcx.code.dto.CarInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.CarInfoDao;
import com.ljcx.code.service.CarInfoService;

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
