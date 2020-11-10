package com.ljcx.api.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 任务列表
 * 
 * @author dm
 * @date 2019-11-19 18:07:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_task")
public class TaskBean extends BaseEntity<Long> {


	/**
	 * 团队ID
	 */
	private Long teamId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 任务类型(1.自主飞行，2.航线飞行)
	 */
	private Integer type;

	/**
	 * 任务描述
	 */
	private String memo;

	/**
	 * 完成状态 0.未完成，1.已完成
	 */
	private Integer completeStatus = 0;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 创建人
	 */
	private Long createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;
	

}
