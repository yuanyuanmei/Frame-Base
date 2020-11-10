package com.ljcx.platform.beans;

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

	

}
