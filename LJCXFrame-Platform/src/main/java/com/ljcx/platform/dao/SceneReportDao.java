package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.platform.beans.SceneReportBean;
import com.ljcx.platform.dto.SceneReportDto;
import com.ljcx.platform.vo.SceneReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 现场上报
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface SceneReportDao extends BaseMapper<SceneReportBean> {

    List<SceneReportVo> listByTeamId(@Param("item") SceneReportDto sceneReportDto);
	
}
