package com.ljcx.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.code.beans.TaskBean;
import com.ljcx.code.beans.TaskRecordsBean;
import com.ljcx.code.dto.TaskDto;
import com.ljcx.code.dao.TaskRecordsDao;
import com.ljcx.code.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.code.dao.TaskDao;
import com.ljcx.code.service.TaskService;

import java.util.List;


@Service
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskBean> implements TaskService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskRecordsDao reocrdsDao;



    @Override
    public IPage<TaskVo> pageList(TaskDto taskDto) {
        IPage<TaskVo> page = new Page<>();
        page.setCurrent(taskDto.getPageNum());
        page.setSize(taskDto.getPageSize());
        return taskDao.pageList(page,taskDto);
    }

    @Override
    public IPage<TaskRecordsBean> recordsList(TaskDto taskDto) {
        IPage<TaskRecordsBean> page = new Page<>();
        page.setCurrent(taskDto.getPageNum());
        page.setSize(taskDto.getPageSize());
        return reocrdsDao.pageList(page,taskDto);
    }

    @Override
    public int TaskCount(List<Long> teamIds) {
        if(teamIds != null && teamIds.size() > 0){
            return taskDao.teamTaskCount(teamIds);
        }
        return 0;
    }


}
