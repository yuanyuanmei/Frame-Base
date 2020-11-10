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
@NotNull(message = "任务记录信息不能为空")
public class TaskRecordsDto extends PageDto {

	/**
	 *  id
	 */
	private Long id;

	@NotNull(message =  "团队ID不能为空")
	private Long teamId;
	/**
	 * 任务ID
	 */
	@NotNull(message =  "任务ID不能为空")
	private Long taskId;

	/**
	 * 航线Id
	 */
	private Long planId;

	/**
	 * 无人机id
	 */
	@NotNull(message = "无人机Id不能为空")
	private Long uavId;

	/**
	 * 无人机名称
	 */
	@NotBlank(message = "无人机名称不能为空")
	private String uavName;

	/**
	 * 执行用户
	 */
	@NotNull(message = "执行者Id不能为空")
	private Long performUser;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 飞行时长
	 */
	private Long flightSeconds;

	/**
	 * 飞行距离
	 */
	private Double flightLength;


	private Long createUser;

	/**
	 * 成果ID集合
	 */
	private List<Long> fileIds;

	/**
	 * 日志ID集合
	 */
	private List<Long> logIds;

}
