package com.ljcx.platform.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "任务航线规划信息不能为空")
public class RoutesPlanDto extends PageDto {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 任务id
	 */
	private Long taskId;
	/**
	 * 航线类型
	 */
	private Integer type;
	/**
	 * 航线
	 */
	private String routes;
}
