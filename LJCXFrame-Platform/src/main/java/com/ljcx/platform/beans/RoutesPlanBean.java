package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 任务航线规划
 * 
 * @author dm
 * @date 2020-03-05 10:47:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_routes_plan")
public class RoutesPlanBean extends BaseEntity<Long> {

	
	/**
	 * 任务id
	 */
	private Long taskId;
	
	/**
	 * 航线类型
	 */
	private Integer type;
	
	/**
	 * 
	 */
	private String routes;
	

}
