package com.ljcx.platform.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
@NotNull(message = "飞行区域信息不能为空")
public class FlyAreaDto extends PageDto {

	/**
	 * 类型
	 */
	@NotNull(message = "飞行区域类型不能为空")
	private Integer category;

	/**
	 * 最大飞行高度
	 */
	private Double maximumFlightHeight = 0.0;

	public FlyAreaDto(Integer category) {
		this.category = category;
	}

	public FlyAreaDto(Integer category, Double maximumFlightHeight) {
		this.category = category;
		this.maximumFlightHeight = maximumFlightHeight;
	}
}
