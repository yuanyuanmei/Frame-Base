package com.ljcx.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.platform.beans.TaskBean;
import com.ljcx.platform.beans.TaskRecordsBean;
import com.ljcx.platform.dao.TaskDao;
import com.ljcx.platform.dao.TaskRecordsDao;
import com.ljcx.platform.dto.TaskDto;
import com.ljcx.platform.service.TaskService;
import com.ljcx.common.utils.IDUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TaskVo;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.UserBaseDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskBean> implements TaskService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskRecordsDao reocrdsDao;

    @Autowired
    private UserBaseDao baseDao;

    @Override
    public IPage<TaskVo> pageList(TaskDto taskDto) {
        IPage<TaskVo> page = new Page<>();
        page.setCurrent(taskDto.getPageNum());
        page.setSize(taskDto.getPageSize());
        return taskDao.pageList(page,taskDto);
    }

    @Override
    public List<TaskVo> list(TaskDto taskDto) {
        return taskDao.pageList(taskDto);
    }

    @Override
    public TaskVo info(Long id) {
        TaskVo info = taskDao.info(id);
        Map<String, Number> dataMap = reocrdsDao.dataByTaskId(id);
        info.setFlightCount(NumberUtils.toInt(dataMap.get("flightCount")+""));
        info.setFlightLength(NumberUtils.toInt(dataMap.get("flightLength")+""));
        info.setFlightAvgSeconds(NumberUtils.toLong(dataMap.get("flightAvgSeconds")+""));
        info.setFlightSumSeconds(NumberUtils.toLong(dataMap.get("flightSumSeconds")+""));
        return info;
    }

    @Override
    public ResponseInfo saveFlyRecord(TaskDto taskDto) {
        TaskBean taskBean = generator.convert(taskDto, TaskBean.class);
        //新增主表
        if(taskBean.getId() == null){
            //type 1 为自主飞行
            taskBean.setType(1);
            taskBean.setName("自主飞行"+ IDUtils.getItemID(6));
            taskDao.insert(taskBean);
        }else{
            taskDao.updateById(taskBean);
        }
        //新增任务记录表
        TaskRecordsBean recordsBean = generator.convert(taskDto, TaskRecordsBean.class);
        recordsBean.setTaskId(taskBean.getId());
        recordsBean.setPerformUser(taskBean.getCreateUser());
        reocrdsDao.insert(recordsBean);

        //更改用户时长和里程数
        UserBaseBean baseBean = baseDao.selectById(taskDto.getCreateUser());
        baseDao.updateById(baseBean);
        return new ResponseInfo().success("保存成功");
    }

    @Override
    public JSONObject initChart(Long teamId) {
        List<MapVo> completeStatusList = taskDao.groupByCompleteStatus(teamId);
        List<MapVo> typeList = taskDao.groupByType(teamId);
        List<MapVo> flightHoursList = taskDao.groupByFlightHours(teamId);
        double totalFlightHours = taskDao.totalFlightHours(teamId);
        int totalCount = 0;
        for(MapVo item:completeStatusList){
            totalCount += item.getValue();
        }
        Map<String, Long> completeStatusMap = completeStatusList.stream().collect(Collectors.toMap(MapVo::getName, MapVo::getValue));
        Map<String, Long> flightHoursMap = flightHoursList.stream().collect(Collectors.toMap(MapVo::getName, MapVo::getValue));
        JSONObject resp = new JSONObject();
        resp.put("totalFlightHours",totalFlightHours);
        resp.put("completeStatusMap",completeStatusMap);
        resp.put("typeList",typeList);
        resp.put("totalCount",totalCount);
        resp.put("flightHoursMap",flightHoursMap);
        return resp;
    }

    @Override
    public TaskVo infoBean(Long id) {
        return taskDao.infoBean(id);
    }

}
