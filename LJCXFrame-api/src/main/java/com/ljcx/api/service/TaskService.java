package com.ljcx.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.TaskBean;
import com.ljcx.api.beans.TaskRecordsBean;
import com.ljcx.api.dto.TaskDto;
import com.ljcx.api.dto.TaskLogDto;
import com.ljcx.api.dto.TaskRecordsDto;
import com.ljcx.api.vo.TaskLogVo;
import com.ljcx.api.vo.TaskVo;
import com.ljcx.common.utils.ResponseInfo;


/**
 * 任务列表
 *
 * @author dm
 * @date 2019-11-19 18:07:43
 */
public interface TaskService extends IService<TaskBean> {

    IPage<TaskVo> pageList(TaskDto taskDto);

    IPage<TaskRecordsBean> recordsList(TaskDto taskDto);

    ResponseInfo saveRecords(TaskRecordsDto taskRecordsDto);

    JSONObject initChart(Long teamId);

    TaskVo info(Long id);

    ResponseInfo save(TaskDto taskDto);

    ResponseInfo saveResult(TaskRecordsDto recordsDto);

    TaskLogVo saveLog(TaskLogDto logDto);
}

