package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.SceneReportBean;
import com.ljcx.api.dto.SceneReportDto;
import com.ljcx.common.utils.ResponseInfo;

import java.util.List;

/**
 * 现场上报
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface SceneReportService extends IService<SceneReportBean> {

    IPage<SceneReportBean> pageList(SceneReportDto sceneReportDto);

    ResponseInfo save(SceneReportDto sceneReportDto);

}

