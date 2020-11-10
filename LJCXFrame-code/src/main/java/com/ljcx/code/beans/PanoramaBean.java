package com.ljcx.code.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.base.BaseEntity;
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
	@TableField(value = "is_share")
	private Integer shareStatus = 2;

	/**
	 * 上传人
	 */
	private Long createUser;
	

}
