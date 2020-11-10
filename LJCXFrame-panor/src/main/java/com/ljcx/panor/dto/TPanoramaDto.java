package com.ljcx.panor.dto;

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
@NotNull(message = "全景图表信息不能为空")
public class TPanoramaDto extends PageDto {

	/**
	 *  全景图id
	 */
	@NotNull(message = "全景图id不能为空")
	private Integer panoId;

	private Integer loginUser = 0;
	/**
	 * 全景图标题
	 */
	private String panoTitle;
	/**
	 * 全景图在线地址
	 */
	private String panoUrl;
	/**
	 * 缩略图地址
	 */
	private String panoThumb;
	/**
	 * 地址
	 */
	private String panoAddress;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 发布人ID
	 */
	@NotNull(message = "用户id不能为空")
	private Integer userId;
	/**
	 * 浏览次数
	 */
	private Integer viewsNumber;
	/**
	 * 发布时间
	 */
	private Date createdate;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
