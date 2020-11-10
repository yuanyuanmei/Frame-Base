package com.ljcx.panor.dao;

import com.ljcx.panor.beans.RefUserPanoBean;
import com.ljcx.panor.dto.RefUserPanoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 用户-全景图关联表（收藏）
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */

public interface RefUserPanoDao extends BaseMapper<RefUserPanoBean> {

    IPage<RefUserPanoBean> pageList(IPage<RefUserPanoBean> page, @Param("item") RefUserPanoDto refUserPanoDto);
	
}
