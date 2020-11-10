package com.ljcx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.beans.CarInfoBean;
import com.ljcx.api.dao.CarInfoDao;
import com.ljcx.api.dao.TeamInfoDao;
import com.ljcx.api.dto.CarInfoDto;
import com.ljcx.api.dto.TeamInfoDto;
import com.ljcx.api.service.CarInfoService;
import com.ljcx.api.shiro.util.UserUtil;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("carInfoService")
public class CarInfoServiceImpl extends ServiceImpl<CarInfoDao, CarInfoBean> implements CarInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private CarInfoDao carInfoDao;

    @Autowired
    private TeamInfoDao teamInfoDao;

    @Autowired
    private UserUtil userUtil;

    @Override
    public IPage<CarInfoBean> pageList(CarInfoDto carInfoDto) {
        IPage<CarInfoBean> page = new Page<>();
        page.setCurrent(carInfoDto.getPageNum());
        page.setSize(carInfoDto.getPageSize());
        return carInfoDao.pageList(page,carInfoDto);
    }

    @Override
    @Transactional
    public ResponseInfo saveCar(CarInfoDto carInfoDto) {
        CarInfoBean bean = generator.convert(carInfoDto, CarInfoBean.class);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("no", carInfoDto.getNo());
        List list = carInfoDao.selectList(wrapper);
        if (list.size() == 0) {
            //新增主表uav
            int uav_insert = carInfoDao.insert(bean);
            //新增团队关联表
            TeamInfoDto teamInfoDto = new TeamInfoDto();
            teamInfoDto.setMIds(Arrays.asList(bean.getId()));
            teamInfoDto.setMType(1);
            teamInfoDto.setCreateUser(userUtil.getCurrentUser().getId());
            teamInfoDto.setId(carInfoDto.getTeamId());
            teamInfoDao.bindTeam(teamInfoDto);
        }
        return new ResponseInfo(bean);
    }

}
