package com.ljcx.code.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 任务记录
 * 
 * @author dm
 * @date 2019-11-19 18:07:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_task_records")
public class TaskRecordsBean extends BaseEntity<Long> {

	
	/**
	 * 任务ID
	 */
	private Long taskId;
	
	/**
	 * 执行用户
	 */
	private Long performUser;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 完成状态（0.未完成，1.正在进行，2.已完成）
	 */
	private Integer completeStatus;

	

}
