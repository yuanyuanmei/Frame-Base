package com.ljcx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.beans.SceneReportBean;
import com.ljcx.api.dao.SceneReportDao;
import com.ljcx.api.dto.SceneReportDto;
import com.ljcx.api.service.SceneReportService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.List;

@Service
public class SceneReportServiceImpl extends ServiceImpl<SceneReportDao, SceneReportBean> implements SceneReportService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SceneReportDao sceneReportDao;

    @Autowired
    private SysFileDao fileDao;

    @Override
    public IPage<SceneReportBean> pageList(SceneReportDto sceneReportDto) {
        IPage<SceneReportBean> page = new Page<>();
        page.setCurrent(sceneReportDto.getPageNum());
        page.setSize(sceneReportDto.getPageSize());
        return sceneReportDao.pageList(page,sceneReportDto);
    }

    @Override
    @Transactional
    public ResponseInfo save(SceneReportDto sceneReportDto) {
        SceneReportBean bean = generator.convert(sceneReportDto, SceneReportBean.class);
        sceneReportDao.insert(bean);
        //修改附件表
        if(sceneReportDto.getFileIds() != null && sceneReportDto.getFileIds().size() > 0){
            fileDao.updateByMid(sceneReportDto.getFileIds(), bean.getId(), "REPORT");
        }
        return new ResponseInfo().success("上传成功");
    }



}
