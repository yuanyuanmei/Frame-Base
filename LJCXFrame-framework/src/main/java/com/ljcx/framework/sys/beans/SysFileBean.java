package com.ljcx.framework.sys.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 附件列表
 * 
 * @author dm
 * @date 2019-11-19 14:42:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "sys_file")
public class SysFileBean extends BaseEntity<Long> {

	
	/**
	 * 关联id
	 */
	private Long mId;
	
	/**
	 * 关联来源
	 */
	private String mSrc;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件大小
	 */
	private Long size;
	
	/**
	 * 上传文件路径
	 */
	private String filePath;
	
	/**
	 * 缩略图url
	 */
	private String url;

	/**
	 * 缩略图上传文件路径
	 */
	private String thumbPath;

	/**
	 * url
	 */
	private String thumbUrl;
	
	/**
	 * 文件后缀名
	 */
	private String suffix;

	@TableField(exist = false,value = "is_delete")
	private Integer deleteStatus;

	

}
