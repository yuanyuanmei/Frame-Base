package com.ljcx.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
@NotNull(message = "现场上报信息不能为空")
public class SceneReportDto extends PageDto {

	/**
	 * 
	 */
	private Long id;
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
	private Long createUser;

	/**
	 * 团队Id
	 */
	private Long teamId;

	/**
	 * 日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;


}
