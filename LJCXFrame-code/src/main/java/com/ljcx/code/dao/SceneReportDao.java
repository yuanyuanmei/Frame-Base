package com.ljcx.code.dao;

import com.ljcx.code.beans.SceneReportBean;
import com.ljcx.code.dto.SceneReportDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.vo.SceneReportVo;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

/**
 * 现场上报
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface SceneReportDao extends BaseMapper<SceneReportBean> {

    IPage<SceneReportVo> pageList(IPage<SceneReportVo> page, @Param("item") SceneReportDto sceneReportDto, @Param("currentUser") UserBaseBean userBaseBean);
	
}
