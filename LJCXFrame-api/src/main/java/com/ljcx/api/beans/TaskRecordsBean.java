package com.ljcx.api.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务记录
 *
 * @author dm
 * @date 2020-03-05 10:47:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_task_records")
public class TaskRecordsBean extends BaseEntity<Long> {


	/**
	 * 任务id
	 */
	private Long taskId;

	/**
	 * 航线ID
	 */
	private Long planId;

	/**
	 * 无人机id
	 */
	private Long uavId;

	/**
	 * 无人机名称
	 */
	private String uavName;

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
	 * 飞行距离
	 */
	private double flightLength;

	/**
	 * 飞行时长
	 */
	private Long flightSeconds;


}
