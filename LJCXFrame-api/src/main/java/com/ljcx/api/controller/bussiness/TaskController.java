package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.beans.TaskBean;
import com.ljcx.api.beans.TaskLogBean;
import com.ljcx.api.dto.TaskDto;
import com.ljcx.api.dto.TaskLogDto;
import com.ljcx.api.dto.TaskRecordsDto;
import com.ljcx.api.enums.TaskTypeEnums;
import com.ljcx.api.service.TaskService;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.constants.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 任务列表
 *
 * @author dm
 * @date 2019-11-19 18:07:43
 */
@RestController
@RequestMapping("/task/list")
@Slf4j
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private IGenerator generator;

    /**
     * 分页查询列表
     * @param info
     * info(taskDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    //@RequiresPermissions("task:list:query")
    public ResponseInfo pageList(@RequestBody String info) {
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        return new ResponseInfo(taskService.pageList(taskDto));
    }

    @PostMapping("/info")
    public ResponseInfo info(@RequestBody TaskDto taskDto) {
        if(taskDto == null || taskDto.getId() == null){
            return new ResponseInfo().failed("任务id不能为空");
        }
        return new ResponseInfo(taskService.info(taskDto.getId()));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/saveLog")
    //@RequiresPermissions("task:list:add")
    @ValidateCustom(TaskLogDto.class)
    public ResponseInfo saveLog(@RequestBody String info, HttpServletRequest request){
        TaskLogDto logDto = JSONObject.parseObject(info, TaskLogDto.class);
        logDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return new ResponseInfo(taskService.saveLog(logDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("task:list:add")
    @ValidateCustom(TaskDto.class)
    public ResponseInfo save(@RequestBody String info, HttpServletRequest request){
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        taskDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return taskService.save(taskDto);
    }

    /**
     * 上传飞行记录
     * @param info
     * @return
     */
    @PostMapping("/saveRecords")
    //@RequiresPermissions("task:list:add")
    @ValidateCustom(TaskRecordsDto.class)
    public ResponseInfo saveRecords(@RequestBody String info,HttpServletRequest request){
        TaskRecordsDto taskRecordsDto = JSONObject.parseObject(info, TaskRecordsDto.class);
        taskRecordsDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return taskService.saveRecords(taskRecordsDto);
    }

    /**
     * 上传成果记录
     * @param info
     * @return
     */
    @PostMapping("/saveResult")
    //@RequiresPermissions("task:list:add")
    public ResponseInfo saveResult(@RequestBody String info,HttpServletRequest request){
        TaskRecordsDto recordsDto = JSONObject.parseObject(info, TaskRecordsDto.class);
        if(recordsDto.getTaskId() == null){
            return new ResponseInfo().failed("任务ID不能为空");
        }
        if(recordsDto.getTeamId() == null){
            return new ResponseInfo().failed("团队ID不能为空");
        }
        recordsDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return taskService.saveResult(recordsDto);
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    //@RequiresPermissions("task:list:del")
    public ResponseInfo del(@RequestBody String info){
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        taskService.removeByIds(taskDto.getIds());
        return new ResponseInfo().success("删除成功");
    }

    /**
     * 任务记录
     * @param info
     * @return
     */
    @PostMapping("/recordsList")
    //@RequiresPermissions("task:list:query")
    public ResponseInfo recordsList(@RequestBody String info){
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        return new ResponseInfo(taskService.recordsList(taskDto));
    }

    /**
     * 获得枚举列表
     * @return
     */
    @PostMapping("/enumsList")
    //@RequiresPermissions("task:list:query")
    public ResponseInfo enumsList(){
        return new ResponseInfo(TaskTypeEnums.mapList());
    }

    /**
     * 任务图表
     * @return
     */
    @PostMapping("/initChart")
    public ResponseInfo initChart(@RequestBody TaskDto taskDto) {
        return new ResponseInfo(taskService.initChart(taskDto.getTeamId()));
    }
}
