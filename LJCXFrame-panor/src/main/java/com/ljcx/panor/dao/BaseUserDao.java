package com.ljcx.panor.dao;

import com.ljcx.panor.beans.BaseUserBean;
import com.ljcx.panor.dto.BaseUserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 用户基础表
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */

public interface BaseUserDao extends BaseMapper<BaseUserBean> {

    IPage<BaseUserBean> pageList(IPage<BaseUserBean> page, @Param("item") BaseUserDto baseUserDto);
	
}
