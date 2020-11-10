package com.ljcx.quartz.controller;

import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.quartz.beans.SysJob;
import com.ljcx.quartz.dto.SysJobDto;
import com.ljcx.quartz.service.ISysJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController
{
    private String prefix = "monitor/job";

    @Autowired
    private ISysJobService jobService;


    @PostMapping("/list")
    @ResponseBody
    public ResponseInfo list(SysJobDto job)
    {
        return new ResponseInfo(jobService.selectJobList(job));
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseInfo remove(String ids) throws SchedulerException
    {
        jobService.deleteJobByIds(ids);
        return new ResponseInfo().success("删除成功");
    }

    @GetMapping("/detail/{jobId}")
    public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("name", "job");
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/detail";
    }

    /**
     * 任务调度状态修改
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public ResponseInfo changeStatus(SysJob job) throws SchedulerException
    {
        SysJob newJob = jobService.selectJobById(job.getId());
        newJob.setStatus(job.getStatus());
        return new ResponseInfo(jobService.changeStatus(newJob));
    }

    /**
     * 任务调度立即执行一次
     */
    @PostMapping("/run")
    @ResponseBody
    public ResponseInfo run(SysJob job) throws SchedulerException
    {
        jobService.run(job);
        return new ResponseInfo().success("操作成功");
    }

    /**
     * 新增调度
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存调度
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseInfo addSave(@RequestBody SysJob job) throws SchedulerException
    {
        return new ResponseInfo(jobService.insertJob(job));
    }

    /**
     * 修改调度
     */
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/edit";
    }

    /**
     * 修改保存调度
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseInfo editSave(@RequestBody SysJob job) throws SchedulerException
    {
        return new ResponseInfo(jobService.updateJob(job));
    }

    /**
     * 校验cron表达式是否有效
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(SysJob job)
    {
        return jobService.checkCronExpressionIsValid(job.getCronExpression());
    }
}
