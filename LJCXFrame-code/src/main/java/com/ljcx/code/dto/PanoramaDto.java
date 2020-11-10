package com.ljcx.code.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "全景图信息不能为空")
public class PanoramaDto extends PageDto {

		/**
	 * 
	 */
	private Long id;

	private Long teamId;
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
	private String lng;
		/**
	 * 上传人纵坐标
	 */
	private String lat;
		/**
	 * 地址
	 */
	private String address;
		/**
	 * 是否共享，1.共享，2.不共享
	 */
	private Integer shareStatus = 2;
	
	/**
 	* id集合
 	*/
	private List<Long> ids;
}
