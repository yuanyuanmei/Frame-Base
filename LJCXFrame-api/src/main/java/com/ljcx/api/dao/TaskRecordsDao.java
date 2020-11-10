package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.api.beans.TaskRecordsBean;
import com.ljcx.api.dto.TaskDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 任务记录
 * 
 * @author dm
 * @date 2019-11-19 18:07:44
 */

public interface TaskRecordsDao extends BaseMapper<TaskRecordsBean> {

    IPage<TaskRecordsBean> pageList(IPage<TaskRecordsBean> page, @Param("item") TaskDto taskDto);

    List<TaskRecordsBean> listByTaskId(Long taskId);

    Map<String,Number> dataByPlanId(@Param("planId") Long planId);

    Map<String,Number> dataByTaskId(@Param("taskId") Long taskId);
	
}
