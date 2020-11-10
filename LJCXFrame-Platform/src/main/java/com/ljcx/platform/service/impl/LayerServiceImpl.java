package com.ljcx.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.beans.LayerBean;
import com.ljcx.platform.dao.LayerDao;
import com.ljcx.platform.service.LayerService;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.LayerBeanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class LayerServiceImpl extends ServiceImpl<LayerDao, LayerBean> implements LayerService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private LayerDao layerDao;


    @Override
    public List<LayerBeanVo> listByTeamId(Long teamId) {
        return layerDao.listByTeamId(teamId, UserUtil.getCurrentUser());
    }
}
