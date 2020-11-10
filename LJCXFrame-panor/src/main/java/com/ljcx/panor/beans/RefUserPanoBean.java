package com.ljcx.panor.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户-全景图关联表（收藏）
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ref_user_pano")
public class RefUserPanoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 全景图ID
	 */
	private Integer panoId;
	

}
