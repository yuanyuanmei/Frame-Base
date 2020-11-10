package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljcx.platform.beans.LayerBean;
import com.ljcx.platform.vo.LayerBeanVo;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface LayerDao extends BaseMapper<LayerBean> {


    List<LayerBeanVo> listByTeamId(@Param("teamId") Long teamId , @Param("currentUser") UserBaseBean userBaseBean);
}
