package com.ljcx.code.dao;

import com.ljcx.code.beans.PanoramaBean;
import com.ljcx.code.dto.PanoramaDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface PanoramaDao extends BaseMapper<PanoramaBean> {

    IPage<PanoramaBean> pageList(IPage<PanoramaBean> page, @Param("item") PanoramaDto panoramaDto, @Param("currentUser") UserBaseBean userBaseBean);

    List<PanoramaBean> listBean(@Param("item") PanoramaDto panoramaDto);
}
