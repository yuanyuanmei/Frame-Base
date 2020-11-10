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
@NotNull(message = "任务列表信息不能为空")
public class TaskDto extends PageDto {

	/**
	 * id
	 */
	private Long id;
	/**
	 * taskId
	 */
	private Long taskId;
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
 	* id集合
 	*/
	private List<Long> ids;
}
