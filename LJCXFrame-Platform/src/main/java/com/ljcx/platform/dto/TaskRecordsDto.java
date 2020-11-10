package com.ljcx.platform.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	/**
	 * 任务ID
	 */
	private Long taskId;
	/**
	 * 执行用户
	 */
	private Long performUser;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 完成状态（0.未完成，1.正在进行，2.已完成）
	 */
	private Integer completeStatus;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
