package com.ljcx.code.dto;

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
	 * 
	 */
	private Long id;
		/**
	 * 类型
	 */
	private Integer type;
		/**
	 * 内容
	 */
	private String content;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
