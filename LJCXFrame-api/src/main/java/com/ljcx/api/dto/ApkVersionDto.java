package com.ljcx.api.dto;

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
@NotNull(message = "版本信息不能为空")
public class ApkVersionDto extends PageDto {

	/**
	 * 主键，id，唯一
	 */
	private String id;
	/**
	 * 版本名称，用于UI显示
	 */
	private String versionName;
	/**
	 * 版本号
	 */
	private Integer versionCode;
	/**
	 * 新版apk名称
	 */
	private String apkName;
	/**
	 * 是否强制更新，1是，0否
	 */
	private Integer isForceUpdate;
	/**
	 * 新版apk说明
	 */
	private String memo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	/**
	 * appkey
	 */
	private String appKey;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
