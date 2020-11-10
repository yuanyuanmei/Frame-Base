package com.ljcx.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.TaskBean;
import com.ljcx.platform.beans.TaskRecordsBean;
import com.ljcx.platform.dto.TaskDto;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.platform.vo.TaskVo;

import java.util.List;


/**
 * 任务列表
 *
 * @author dm
 * @date 2019-11-19 18:07:43
 */
public interface TaskService extends IService<TaskBean> {

    IPage<TaskVo> pageList(TaskDto taskDto);

    List<TaskVo> list(TaskDto taskDto);

    TaskVo info(Long id);

    ResponseInfo saveFlyRecord(TaskDto taskDto);

    JSONObject initChart(Long teamId);

    TaskVo infoBean(Long id);
}

