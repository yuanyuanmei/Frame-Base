package com.ljcx.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.beans.TaskBean;
import com.ljcx.api.beans.TaskLogBean;
import com.ljcx.api.beans.TaskRecordsBean;
import com.ljcx.api.dao.TaskDao;
import com.ljcx.api.dao.TaskLogDao;
import com.ljcx.api.dao.TaskRecordsDao;
import com.ljcx.api.dto.TaskDto;
import com.ljcx.api.dto.TaskLogDto;
import com.ljcx.api.dto.TaskRecordsDto;
import com.ljcx.api.service.TaskService;
import com.ljcx.api.vo.MapVo;
import com.ljcx.api.vo.TaskLogVo;
import com.ljcx.api.vo.TaskVo;
import com.ljcx.common.constant.TaskConstant;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.UserBaseDao;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskBean> implements TaskService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskRecordsDao reocrdsDao;

    @Autowired
    private UserBaseDao baseDao;

    @Autowired
    private TaskLogDao logDao;

    @Autowired
    private SysFileDao fileDao;

    @Autowired
    private ActivemqProducer producer;

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
    @Transactional
    public ResponseInfo saveRecords(TaskRecordsDto taskRecordsDto) {
        TaskRecordsBean taskRecordsBean = generator.convert(taskRecordsDto, TaskRecordsBean.class);

        //新增任务记录表
        if(taskRecordsBean.getId() == null || taskRecordsBean.getId() == 0){
            reocrdsDao.insert(taskRecordsBean);
        }else{
            //任务记录修改
            reocrdsDao.updateById(taskRecordsBean);
            //绑定任务日志
            if(taskRecordsDto.getLogIds() != null && taskRecordsDto.getLogIds().size() > 0){
                logDao.updateByResultId(taskRecordsBean.getId(),taskRecordsDto.getLogIds());
            }
            //更改用户时长和里程数
            UserBaseBean baseBean = baseDao.selectById(taskRecordsDto.getPerformUser());
            baseBean.setMileage(baseBean.getMileage()+taskRecordsBean.getFlightLength());
            baseBean.setHours(baseBean.getHours()+taskRecordsBean.getFlightSeconds());
            baseDao.updateById(baseBean);
        }


        return new ResponseInfo(taskRecordsBean);
    }

    @Override
    public JSONObject initChart(Long teamId) {
        List<MapVo> completeStatusList = taskDao.groupByCompleteStatus(teamId);
        List<MapVo> typeList = taskDao.groupByType(teamId);
        List<MapVo> flightHoursList = taskDao.groupByFlightHours(teamId);
        double totalFlightHours = taskDao.totalFlightHours(teamId);

        Map<String, Long> completeStatusMap = completeStatusList.stream().collect(Collectors.toMap(MapVo::getName, MapVo::getValue));
        Map<String, Long> flightHoursMap = flightHoursList.stream().collect(Collectors.toMap(MapVo::getName, MapVo::getValue));
        JSONObject resp = new JSONObject();
        resp.put("totalFlightHours",totalFlightHours);
        resp.put("completeStatusMap",completeStatusMap);
        resp.put("typeList",typeList);
        resp.put("flightHoursMap",flightHoursMap);
        return resp;
    }

    @Override
    public TaskVo info(Long id) {
        TaskVo info = taskDao.info(id);
        if(info.getLogList() != null){
            info.getLogList().add(new TaskLogVo(info.getId(),TaskConstant.CREATE_TASK.getCode(),TaskConstant.CREATE_TASK.getValue(),info.getUserName(),info.getCreateTime()));
            if(info.getStartTime() != null){
                info.getLogList().add(new TaskLogVo(info.getId(),TaskConstant.START_TASK.getCode(),TaskConstant.START_TASK.getValue(),info.getUserName(),info.getStartTime()));
            }
            if(info.getEndTime() != null){
                info.getLogList().add(new TaskLogVo(info.getId(),TaskConstant.END_TASK.getCode(),TaskConstant.END_TASK.getValue(),info.getUserName(),info.getEndTime()));
            }
            Collections.sort(info.getLogList(),new Comparator<TaskLogVo>() {
                @Override
                public int compare(TaskLogVo o1, TaskLogVo o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
        }
        Map<String, Number> dataMap = reocrdsDao.dataByTaskId(id);
        info.setFlightCount(NumberUtils.toInt(dataMap.get("flightCount")+""));
        info.setFlightLength(NumberUtils.toInt(dataMap.get("flightLength")+""));
        info.setFlightAvgSeconds(NumberUtils.toLong(dataMap.get("flightAvgSeconds")+""));
        info.setFlightSumSeconds(NumberUtils.toLong(dataMap.get("flightSumSeconds")+""));
        return info;
    }

    @Override
    @Transactional
    public ResponseInfo save(TaskDto taskDto) {
        TaskBean taskBean = generator.convert(taskDto,TaskBean.class);
        TaskBean beanById;
        if(taskBean.getId() == null){
            taskDao.insert(taskBean);
            beanById = taskDao.selectById(taskBean.getId());
            TaskLogVo logVo = new TaskLogVo(taskDto.getTeamId(),taskBean.getId(),taskBean.getName(), TaskConstant.CREATE_TASK.getCode(), TaskConstant.CREATE_TASK.getValue()
                    , baseDao.selectById(taskDto.getCreateUser()).getNickname(),new Date());
            producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
        }else{
            beanById = taskDao.selectById(taskBean.getId());
            //已完成发送完成消息
            if(taskBean.getCompleteStatus() != null && taskBean.getCompleteStatus() == 1){
                taskBean.setEndTime(new Date());
                TaskLogVo logVo = new TaskLogVo(taskDto.getTeamId(),beanById.getId(),beanById.getName(), TaskConstant.END_TASK.getCode(),TaskConstant.END_TASK.getValue()
                        , baseDao.selectById(taskDto.getCreateUser()).getNickname(),new Date());
                producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
            }
            taskDao.updateById(taskBean);
        }
        return new ResponseInfo(beanById);
    }

    @Override
    public ResponseInfo saveResult(TaskRecordsDto recordsDto) {
        //新增任务成果
        if(recordsDto.getFileIds() != null && recordsDto.getFileIds().size() > 0){
            TaskLogBean logBean = new TaskLogBean(recordsDto.getTaskId(), recordsDto.getId(), TaskConstant.UPLOAD_RESULT.getCode(), TaskConstant.UPLOAD_RESULT.getValue(), recordsDto.getCreateUser());
            logDao.insert(logBean);
            fileDao.updateByMid(recordsDto.getFileIds(), logBean.getId(), "TASK");
            TaskLogVo logVo = logDao.info(logBean.getId());
            logVo.setTeamId(recordsDto.getTeamId());
            producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
            return new ResponseInfo(logVo);
        }
        return new ResponseInfo().failed("文件ID为空");
    }

    @Override
    public TaskLogVo saveLog(TaskLogDto logDto) {
        TaskLogBean logBean = generator.convert(logDto, TaskLogBean.class);
        logBean.setName(TaskConstant.codeOf(logDto.getType()));
        logDao.insert(logBean);
        TaskLogVo logVo = logDao.info(logBean.getId());
        logVo.setTeamId(logDto.getTeamId());
        producer.sendTopic("topic_task_log", JSON.toJSONString(logVo));
        return logVo;
    }

}
