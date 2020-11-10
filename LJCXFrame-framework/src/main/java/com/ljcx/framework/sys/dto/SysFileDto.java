package com.ljcx.framework.sys.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "附件列表信息不能为空")
public class SysFileDto extends PageDto {

	/**
	 * id
	 */
	private Long id;
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
	private Integer size;
	/**
	 * 上传文件路径
	 */
	private String filePath;
	/**
	 * url
	 */
	private String url;
	/**
	 * 文件后缀名
	 */
	private String suffix;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
