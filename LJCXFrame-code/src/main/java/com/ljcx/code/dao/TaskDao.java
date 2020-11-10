package com.ljcx.code.dao;

import com.ljcx.code.beans.TaskBean;
import com.ljcx.code.dto.TaskDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
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

    int teamTaskCount(@Param("teamIds")List<Long> teamIds);
	
}
