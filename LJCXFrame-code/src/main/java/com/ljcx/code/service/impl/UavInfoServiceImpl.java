package com.ljcx.code.service.impl;

import com.ljcx.code.beans.UavInfoBean;
import com.ljcx.code.dto.UavInfoDto;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.code.vo.UavInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.UavInfoDao;
import com.ljcx.code.service.UavInfoService;

import java.util.List;

@Service("uavInfoService")
public class UavInfoServiceImpl extends ServiceImpl<UavInfoDao, UavInfoBean> implements UavInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UavInfoDao uavInfoDao;

    @Override
    public IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto) {
        IPage<UavInfoBean> page = new Page<>();
        page.setCurrent(uavInfoDto.getPageNum());
        page.setSize(uavInfoDto.getPageSize());
        return uavInfoDao.pageList(page,uavInfoDto, UserUtil.getCurrentUser());
    }

    @Override
    public List<UavInfoVo> equipUavList() {
        return uavInfoDao.equipUavList();
    }

}
