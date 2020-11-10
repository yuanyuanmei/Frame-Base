package com.ljcx.code.dao;

import com.ljcx.code.beans.TaskRecordsBean;
import com.ljcx.code.dto.TaskDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 任务记录
 * 
 * @author dm
 * @date 2019-11-19 18:07:44
 */

public interface TaskRecordsDao extends BaseMapper<TaskRecordsBean> {

    IPage<TaskRecordsBean> pageList(IPage<TaskRecordsBean> page, @Param("item") TaskDto taskDto);
	
}
