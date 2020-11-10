package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.platform.beans.TaskBean;
import com.ljcx.platform.beans.TaskLogBean;
import com.ljcx.platform.dto.TaskDto;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务日志表
 * 
 * @author dm
 * @date 2019-11-19 18:07:43
 */

public interface TaskLogDao extends BaseMapper<TaskLogBean> {

    List<TaskLogBean> listByTaskId(@Param("taskId")Long taskId);

}
