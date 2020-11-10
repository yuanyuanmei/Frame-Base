package com.ljcx.api.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * ljcx_apk_version
 * 
 * @author dm
 * @date 2019-11-29 12:05:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_apk_version")
public class ApkVersionBean extends BaseEntity<Long> {

	
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
	

}
