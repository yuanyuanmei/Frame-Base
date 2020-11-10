package com.ljcx.panor.beans;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 全景图表
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "t_panorama")
public class TPanoramaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer panoId;
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
	private Integer userId;
	
	/**
	 * 浏览次数
	 */
	private Integer viewsNumber;
	
	/**
	 * 发布时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createdate;

	/**
	 * 全景英文标题
	 */
	private String panoTitleEn;

	/**
	 * 全景英文地址
	 */
	private String panoAddressEn;
	

}
