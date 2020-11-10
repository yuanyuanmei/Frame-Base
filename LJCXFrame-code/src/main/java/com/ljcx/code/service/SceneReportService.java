package com.ljcx.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.code.beans.SceneReportBean;
import com.ljcx.code.dto.SceneReportDto;
import com.ljcx.code.vo.SceneReportVo;

/**
 * 现场上报
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface SceneReportService extends IService<SceneReportBean> {

    IPage<SceneReportVo> pageList(SceneReportDto sceneReportDto);
}

