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
import java.util.Date;

/**
 * 用户基础表
 * 
 * @author dm
 * @date 2020-05-27 10:12:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "base_user")
public class BaseUserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Integer userId;
	
	/**
	 * 用户账号
	 */
	private String userAccount;
	
	/**
	 * 用户密码
	 */
	private String userPwd;
	
	/**
	 * 盐值
	 */
	private String salt;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 用户呢称
	 */
	private String userMoce;
	
	/**
	 * 是否是管理者: 0-非管理员 1-管理员
	 */
	private Integer userAdmin;
	
	/**
	 * 用户状态: 0-锁定禁用 1-启用授权
	 */
	private Integer userState;
	
	/**
	 * 创建时间
	 */
	private Date createdate;
	
	/**
	 * 最后一次登陆时间
	 */
	private Date lastTime;
	

}
