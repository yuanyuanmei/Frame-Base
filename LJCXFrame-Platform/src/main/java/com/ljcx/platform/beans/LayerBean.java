package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 所有图层
 * 
 * @author dm
 * @date 2019-12-09 11:42:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_layer")
public class LayerBean extends BaseEntity<Long> {

	
	/**
	 * 类型
	 */
	private Integer type;
	
	/**
	 * 名称
	 */
	private String name;
	
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
	private Integer isShare;
	
	/**
	 * 上传人
	 */
	private Long createUser;

	/**
	 * 范围
	 */
	private String extent;

	/**
	 * 最小焦距
	 */
	private Integer minZoom;

	/**
	 * 最大焦距
	 */
	private Integer maxZoom;

	

}
