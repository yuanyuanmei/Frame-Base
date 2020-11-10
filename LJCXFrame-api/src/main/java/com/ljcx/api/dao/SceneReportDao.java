package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.api.beans.SceneReportBean;
import com.ljcx.api.dto.SceneReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 现场上报
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface SceneReportDao extends BaseMapper<SceneReportBean> {

    IPage<SceneReportBean> pageList(IPage<SceneReportBean> page, @Param("item") SceneReportDto sceneReportDto);
	
}
