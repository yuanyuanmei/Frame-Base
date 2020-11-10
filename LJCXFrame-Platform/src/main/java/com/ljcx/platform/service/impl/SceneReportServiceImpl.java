package com.ljcx.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.platform.beans.SceneReportBean;
import com.ljcx.platform.dao.SceneReportDao;
import com.ljcx.platform.dto.SceneReportDto;
import com.ljcx.platform.service.SceneReportService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.vo.SceneReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneReportServiceImpl extends ServiceImpl<SceneReportDao, SceneReportBean> implements SceneReportService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SceneReportDao sceneReportDao;

    @Override
    public List<SceneReportVo> listByTeamId(SceneReportDto sceneReportDto) {
        return sceneReportDao.listByTeamId(sceneReportDto);
    }

}
