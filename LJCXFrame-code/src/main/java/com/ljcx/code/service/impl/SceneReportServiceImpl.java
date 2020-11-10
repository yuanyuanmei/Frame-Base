package com.ljcx.code.service.impl;

import com.ljcx.code.beans.SceneReportBean;
import com.ljcx.code.dto.SceneReportDto;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.code.vo.SceneReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.SceneReportDao;
import com.ljcx.code.service.SceneReportService;

@Service
public class SceneReportServiceImpl extends ServiceImpl<SceneReportDao, SceneReportBean> implements SceneReportService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SceneReportDao sceneReportDao;

    @Override
    public IPage<SceneReportVo> pageList(SceneReportDto sceneReportDto) {
        IPage<SceneReportVo> page = new Page<>();
        page.setCurrent(sceneReportDto.getPageNum());
        page.setSize(sceneReportDto.getPageSize());
        return sceneReportDao.pageList(page,sceneReportDto, UserUtil.getCurrentUser());
    }

}
