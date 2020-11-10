package com.ljcx.api.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 现场上报
 * 
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_scene_report")
public class SceneReportBean extends BaseEntity<Long> {

	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 横坐标
	 */
	private String lng;
	
	/**
	 * 纵坐标
	 */
	private String lat;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 上传人
	 */
	private Long createUser;

	/**
	 * 团队Id
	 */
	private Long teamId;
	

}
