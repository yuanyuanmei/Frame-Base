package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSON;
import com.ljcx.common.constant.TaskConstant;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.annotations.RepeatSubmit;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.beans.TaskBean;
import com.ljcx.platform.dto.TaskDto;
import com.ljcx.platform.service.TaskService;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.TaskLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 图层控制层
 */
@RestController
@RequestMapping("/team/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private IGenerator generator;

    @Autowired
    private ActivemqProducer producer;

    @PostMapping("/pageList")
    public ResponseInfo pageList(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.pageList(taskDto));
    }

    @RepeatSubmit
    @PostMapping("/save")
    public ResponseInfo save(@RequestBody TaskDto taskDto) {
        TaskBean task = generator.convert(taskDto, TaskBean.class);
        boolean saveOrUpdate = task.getId() == null;
        boolean save = taskService.saveOrUpdate(task);
        //发送消息给APP
        if(save){
            TaskBean taskById = taskService.getById(task.getId());
            TaskConstant taskConstant = TaskConstant.EDIT_TASK;
            if(saveOrUpdate){
                taskConstant  = TaskConstant.CREATE_TASK;
            }
            TaskLogVo logVo = new TaskLogVo(taskDto.getTeamId(),taskById.getId(),taskById.getName(),taskConstant.getCode() , taskConstant.getValue()
                    , UserUtil.getCurrentUser().getNickname(),new Date());
            producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
        }
        if(save){
            return new ResponseInfo().success("保存成功");
        }
        return new ResponseInfo().failed("保存失败");
    }

    @PostMapping("/list")
    public ResponseInfo list(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.list(taskDto));
    }

    @PostMapping("/info")
    public ResponseInfo info(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.info(taskDto.getId()));
    }

    @PostMapping("/saveRecords")
    public ResponseInfo saveRecords(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.info(taskDto.getId()));
    }

    @PostMapping("/del")
    public ResponseInfo cancelTask(@RequestBody TaskDto taskDto) {
        TaskBean taskById = taskService.getById(taskDto.getId());
        taskService.removeById(taskDto.getId());
        TaskLogVo logVo = new TaskLogVo(taskDto.getTeamId(),taskById.getId(),taskById.getName(),TaskConstant.CANCEL_TASK.getCode() , TaskConstant.CANCEL_TASK.getValue()
                , UserUtil.getCurrentUser().getNickname(),new Date());
        producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
        return new ResponseInfo().success("取消成功");
    }

    @PostMapping("/initChart")
    public ResponseInfo initChart(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.initChart(taskDto.getTeamId()));
    }



}
