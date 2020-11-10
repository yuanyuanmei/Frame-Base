package com.ljcx.api.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "任务日志信息不能为空")
public class TaskLogDto extends PageDto {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 团队Id
	 */
	@NotNull(message = "团队ID不能为空")
	private Long teamId;
	/**
	 * 任务ID
	 */
	@NotNull(message = "任务ID不能为空")
	private Long taskId;

	private Long recordsId;

	/**
	 * 类型
	 */
	@NotBlank(message = "类型不能为空")
	private String type;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 创建人
	 */
	private Long createUser;
}
