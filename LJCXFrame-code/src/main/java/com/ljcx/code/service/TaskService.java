package com.ljcx.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.beans.TaskBean;
import com.ljcx.code.beans.TaskRecordsBean;
import com.ljcx.code.dto.TaskDto;
import com.ljcx.code.vo.TaskVo;

import java.util.List;


/**
 * 任务列表
 *
 * @author dm
 * @date 2019-11-19 18:07:43
 */
public interface TaskService extends IService<TaskBean> {

    IPage<TaskVo> pageList(TaskDto taskDto);

    IPage<TaskRecordsBean> recordsList(TaskDto taskDto);

    int TaskCount(List<Long> teamIds);


}

