package com.ljcx.panor.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "用户-全景图关联表（收藏）信息不能为空")
public class RefUserPanoDto extends PageDto {

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 全景图ID
	 */
	private Integer panoId;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
