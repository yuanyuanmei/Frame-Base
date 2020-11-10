package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.platform.beans.PanoramaBean;
import com.ljcx.platform.dto.PanoramaDto;
import com.ljcx.platform.vo.PanoramaVo;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface PanoramaDao extends BaseMapper<PanoramaBean> {

    List<PanoramaVo> listByTeamId(@Param("teamId") Long teamId, @Param("currentUser")UserBaseBean userBaseBean);
	
}
