package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dto.PanoramaDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface PanoramaDao extends BaseMapper<PanoramaBean> {

    IPage<PanoramaBean> pageList(IPage<PanoramaBean> page, @Param("item") PanoramaDto panoramaDto);

    List<PanoramaBean> listBean(@Param("item") PanoramaDto panoramaDto);
	
}
