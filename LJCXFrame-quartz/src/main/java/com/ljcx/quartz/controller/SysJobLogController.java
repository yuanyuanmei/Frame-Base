package com.ljcx.quartz.controller;

import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.quartz.beans.SysJobLog;
import com.ljcx.quartz.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度日志操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class SysJobLogController
{
    private String prefix = "monitor/job";

    @Autowired
    private ISysJobLogService jobLogService;

    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseInfo list(SysJobLog jobLog)
    {
        List<SysJobLog> list = jobLogService.selectJobLogList(jobLog);
        return new ResponseInfo(list);
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseInfo remove(String ids)
    {
        return new ResponseInfo(jobLogService.deleteJobLogByIds(ids));
    }

    @GetMapping("/detail/{jobLogId}")
    public String detail(@PathVariable("jobLogId") Long jobLogId, ModelMap mmap)
    {
        mmap.put("name", "jobLog");
        mmap.put("jobLog", jobLogService.selectJobLogById(jobLogId));
        return prefix + "/detail";
    }

    @PostMapping("/clean")
    @ResponseBody
    public ResponseInfo clean()
    {
        jobLogService.cleanJobLog();
        return new ResponseInfo().success("操作成功");
    }
}
