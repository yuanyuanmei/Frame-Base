package com.ljcx.panor.dao;

import com.ljcx.panor.beans.TPanoramaBean;
import com.ljcx.panor.dto.TPanoramaDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 全景图表
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */

public interface TPanoramaDao extends BaseMapper<TPanoramaBean> {

    IPage<TPanoramaBean> pageList(IPage<TPanoramaBean> page, @Param("item") TPanoramaDto tPanoramaDto);
	
}
