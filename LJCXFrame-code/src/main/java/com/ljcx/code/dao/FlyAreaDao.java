package com.ljcx.code.dao;

import com.ljcx.code.beans.FlyAreaBean;
import com.ljcx.code.dto.FlyAreaDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 飞行区域
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */

public interface FlyAreaDao extends BaseMapper<FlyAreaBean> {

    IPage<FlyAreaBean> pageList(IPage<FlyAreaBean> page, @Param("item") FlyAreaDto flyAreaDto);
	
}
