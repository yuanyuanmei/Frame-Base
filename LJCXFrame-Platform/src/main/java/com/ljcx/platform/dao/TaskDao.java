package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.platform.beans.TaskBean;
import com.ljcx.platform.dto.TaskDto;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务列表
 * 
 * @author dm
 * @date 2019-11-19 18:07:43
 */

public interface TaskDao extends BaseMapper<TaskBean> {

    IPage<TaskVo> pageList(IPage<TaskVo> page, @Param("item") TaskDto taskDto);

    List<TaskVo> pageList(@Param("item") TaskDto taskDto);

    TaskVo info(Long id);

    TaskVo infoBean(Long id);

    //统计
    //任务完成分组
    List<MapVo> groupByCompleteStatus(@Param("teamId") Long teamId);

    //任务类型分组
    List<MapVo> groupByType(@Param("teamId") Long teamId);

    //任务飞行时长分组
    List<MapVo> groupByFlightHours(@Param("teamId") Long teamId);

    //任务飞行总时长
    double totalFlightHours(@Param("teamId") Long teamId);

}
