package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import com.ljcx.platform.shiro.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 全景图
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_panorama")
public class PanoramaBean extends BaseEntity<Long> {

	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 图片地址
	 */
	private String url;
	
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
	@TableField(value = "is_share")
	private Integer shareStatus = 2;

	/**
	 * 上传人
	 */
	private Long createUser = UserUtil.getCurrentUser().getId();
	

}
