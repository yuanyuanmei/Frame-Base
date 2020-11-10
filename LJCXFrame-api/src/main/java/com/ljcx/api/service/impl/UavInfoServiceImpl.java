package com.ljcx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.api.dao.TeamInfoDao;
import com.ljcx.api.dao.UavInfoDao;
import com.ljcx.api.dto.TeamInfoDto;
import com.ljcx.api.dto.UavInfoDto;
import com.ljcx.api.service.UavInfoService;
import com.ljcx.api.shiro.util.UserUtil;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.UserBaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("uavInfoService")
public class UavInfoServiceImpl extends ServiceImpl<UavInfoDao, UavInfoBean> implements UavInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UavInfoDao uavInfoDao;

    @Autowired
    private TeamInfoDao teamInfoDao;

    @Autowired
    private UserUtil userUtil;

    @Override
    public IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto) {
        IPage<UavInfoBean> page = new Page<>();
        page.setCurrent(uavInfoDto.getPageNum());
        page.setSize(uavInfoDto.getPageSize());
        return uavInfoDao.pageList(page,uavInfoDto);
    }

    @Override
    @Transactional
    public ResponseInfo saveUav(UavInfoDto uavInfoDto) {
        UavInfoBean bean = generator.convert(uavInfoDto, UavInfoBean.class);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("no",uavInfoDto.getNo());
        List<UavInfoBean> list = uavInfoDao.selectList(wrapper);
        int teamCount = teamInfoDao.teamRelationCount(uavInfoDto.getTeamId(), uavInfoDto.getId(), 1);
        if(list.size() == 0){
            //新增主表uav
            uavInfoDao.insert(bean);
        }else{
            bean = list.get(0);
        }
        //没有注册无人机或新增新的团队则添加团队关系
        if(teamCount == 0 || list.size() == 0){
            //新增团队关联表
            TeamInfoDto teamInfoDto = new TeamInfoDto();
            teamInfoDto.setMIds(Arrays.asList(bean.getId()));
            teamInfoDto.setMType(1);
            teamInfoDto.setCreateUser(uavInfoDto.getCreateUser());
            teamInfoDto.setId(uavInfoDto.getTeamId());
            teamInfoDao.bindTeam(teamInfoDto);
        }

        return new ResponseInfo(bean);
    }

}
