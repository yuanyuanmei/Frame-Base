package com.ljcx.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.platform.beans.PanoramaBean;
import com.ljcx.platform.dao.PanoramaDao;
import com.ljcx.platform.dto.PanoramaDto;
import com.ljcx.platform.service.PanoramaService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.PanoramaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class PanoramaServiceImpl extends ServiceImpl<PanoramaDao, PanoramaBean> implements PanoramaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private PanoramaDao panoramaDao;


    @Override
    public List<PanoramaVo> listByTeamId(Long teamId) {
        return panoramaDao.listByTeamId(teamId, UserUtil.getCurrentUser());
    }

}
