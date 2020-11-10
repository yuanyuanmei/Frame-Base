package com.ljcx.code.controller.bussiness;

import com.ljcx.code.beans.TaskBean;
import com.ljcx.code.dto.TaskDto;
import com.ljcx.code.enums.TaskTypeEnums;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.ljcx.framework.sys.service.IGenerator;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.code.service.TaskService;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 任务列表
 *
 * @author dm
 * @date 2019-11-19 18:07:43
 */
@RestController
@RequestMapping("/task/list")
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
    @RequiresPermissions("task:list:query")
    public ResponseInfo pageList(@RequestBody String info) {
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        return new ResponseInfo(taskService.pageList(taskDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("task:list:add")
    public ResponseInfo save(@RequestBody String info){
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        TaskBean bean = generator.convert(taskDto, TaskBean.class);
        taskService.saveOrUpdate(bean);
        return new ResponseInfo(bean);
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    @RequiresPermissions("task:list:del")
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
    @RequiresPermissions("task:list:query")
    public ResponseInfo recordsList(@RequestBody String info){
        TaskDto taskDto = JSONObject.parseObject(info, TaskDto.class);
        return new ResponseInfo(taskService.recordsList(taskDto));
    }

    /**
     * 获得枚举列表
     * @return
     */
    @PostMapping("/enumsList")
    @RequiresPermissions("task:list:query")
    public ResponseInfo enumsList(){
        return new ResponseInfo(TaskTypeEnums.mapList());
    }
}
