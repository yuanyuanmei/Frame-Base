package com.ljcx.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.beans.LayerBean;
import com.ljcx.code.beans.PanoramaBean;
import com.ljcx.code.dto.LayerDto;
import com.ljcx.code.dto.PanoramaDto;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface LayerDao extends BaseMapper<LayerBean> {

    IPage<LayerBean> pageList(IPage<LayerBean> page, @Param("item") LayerDto layerDto, @Param("currentUser")UserBaseBean userBaseBean);
	
}
