package com.ljcx.api.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 任务日志表
 * 
 * @author dm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_task_log")
public class TaskLogBean extends BaseEntity<Long> {

	
	/**
	 * 任务ID
	 */
	private Long taskId;

	/**
	 * 记录id
	 */
	private Long recordsId;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 创建用户
	 */
	private Long createUser;

	public TaskLogBean(Long taskId,Long recordsId, String type, String name, Long createUser) {
		this.taskId = taskId;
		this.recordsId = recordsId;
		this.type = type;
		this.name = name;
		this.createUser = createUser;
	}

	public TaskLogBean(Long taskId,Long recordsId, String type, String name, Date createTime, Long createUser) {
		super(createTime);
		this.taskId = taskId;
		this.recordsId = recordsId;
		this.type = type;
		this.name = name;
		this.createUser = createUser;
	}
}
