package com.ljcx.quartz.dto;

import com.ljcx.common.constant.ScheduleConstants;
import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "定时任务信息不能为空")
public class SysJobDto extends PageDto {

	private Long id;
	/** 任务名称 */
	@NotBlank(message = "任务名称不能为空")
	@Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
	private String jobName;

	/** 任务组名 */
	private String jobGroup;

	/** 调用目标字符串 */
	@NotBlank(message = "调用目标字符串不能为空")
	@Size(min = 0, max = 1000, message = "调用目标字符串长度不能超过500个字符")
	private String invokeTarget;

	/** cron执行表达式 */
	@NotBlank(message = "Cron执行表达式不能为空")
	@Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
	private String cronExpression;

	/** cron计划策略 */
	private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

	/** 是否并发执行（0允许 1禁止） */
	private String concurrent;

	/** 任务状态（0正常 1暂停） */
	private String status;

	private String remark;



}
