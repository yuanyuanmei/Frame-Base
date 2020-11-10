package com.ljcx.api.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "全景图信息不能为空")
public class PanoramaDto extends PageDto {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 团队Id
	 */
	@NotNull(message = "团队ID不能为空")
	private Long teamId;

	/**
	 * 文件ID
	 */
	@NotNull(message = "文件ID不能为空")
	private Long fileId;
		/**
	 * 名称
	 */
	private String name;
		/**
	 * 图片地址
	 */
	private String url;

	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 生成全景图状态 0，未生成，1.已生成
	 */
	private Integer genStatus;
	
		/**
	 * 上传人横坐标
	 */
	@NotBlank(message = "横坐标不能为空")
	private String lng;
		/**
	 * 上传人纵坐标
	 */
	@NotBlank(message = "纵坐标不能为空")
	private String lat;
		/**
	 * 地址
	 */
	private String address;
		/**
	 * 是否共享，1.共享，2.不共享
	 */
	private Integer shareStatus;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;

	private Long createUser;
}
