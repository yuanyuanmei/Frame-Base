package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 飞行区域
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_fly_area")
public class FlyAreaBean extends BaseEntity<Long> {


	/**
	 * 类型
	 */
	private Integer category;

	/**
	 * 坐标
	 */
	private String coordinate;

	/**
	 * 飞行区域id（来自api）
	 */
	private Long flyZoneId;

	/**
	 * 飞行区域类型（来自api）
	 */
	private String flyZoneType;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 半径
	 */
	private Double radius;

	/**
	 * 原因
	 */
	private String reason;

	/**
	 * 形状
	 */
	private String shape;

	/**
	 * 最大飞行高度
	 */
	private Double maximumFlightHeight;

	/**
	 * 坐标顶点
	 */
	private String vertices;

	/**
	 * 上级id
	 */
	private Long parentId;

	

}
