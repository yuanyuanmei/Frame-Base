package com.ljcx.code.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	 * 任务类型
	 */
	private Integer type;
	
	/**
	 * 任务内容
	 */
	private String content;
	
	/**
	 * 创建人
	 */
	private Long createUser = UserUtil.getCurrentUser().getId();
	

}
