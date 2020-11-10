package com.ljcx.platform.dto;

import com.ljcx.common.utils.PageDto;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.platform.shiro.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	 * task_id
	 */
	private Long taskId;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String memo;
}
