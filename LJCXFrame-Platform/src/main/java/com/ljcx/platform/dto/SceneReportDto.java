package com.ljcx.platform.dto;

import com.ljcx.common.utils.PageDto;
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
@NotNull(message = "现场上报信息不能为空")
public class SceneReportDto extends PageDto {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 团队Id
	 */
	@NotNull(message = "团队Id不能为空")
	private Long teamId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 横坐标
	 */
	private String lng;
	/**
	 * 纵坐标
	 */
	private String lat;
	/**
	 * 地址
	 */
	private String address;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;

	/**
	 * 文件id集合
	 */
	private List<Long> fileIds;

	/**
	 * 创建用户
	 */
	private Long createUser = UserUtil.getCurrentUser().getId();

	/**
	 * 上报时间
	 */
	private Date createTime;

}
