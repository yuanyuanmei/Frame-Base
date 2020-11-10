package com.ljcx.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.SceneReportBean;
import com.ljcx.platform.dto.SceneReportDto;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.platform.vo.SceneReportVo;

import java.util.List;

/**
 * 现场上报
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface SceneReportService extends IService<SceneReportBean> {

    List<SceneReportVo> listByTeamId(SceneReportDto sceneReportDto);
}

