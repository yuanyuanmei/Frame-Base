package com.ljcx.api.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "飞行区域信息不能为空")
public class FlyAreaDto extends PageDto {

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
	 * 子对象
	 */
	private List<FlyAreaDto> child;

}
