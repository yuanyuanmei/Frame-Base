package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljcx.api.beans.TaskLogBean;
import com.ljcx.api.vo.TaskLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务日志表
 * 
 * @author dm
 * @date 2019-11-19 18:07:43
 */

public interface TaskLogDao extends BaseMapper<TaskLogBean> {

    List<TaskLogVo> listByTaskId(@Param("taskId") Long taskId);

    int updateByResultId(@Param("recordsId") Long recordsId, @Param("logIds") List<Long> logIds);

    TaskLogVo info(@Param("id") Long id);
}
