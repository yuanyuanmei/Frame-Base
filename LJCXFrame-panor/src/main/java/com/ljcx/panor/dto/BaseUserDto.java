package com.ljcx.panor.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "用户基础表信息不能为空")
public class BaseUserDto extends PageDto {

	/**
	 * 注册用户ID
	 */
	private Integer userId;
	/**
	 * 用户账号
	 */
	@NotBlank(message = "用户账号不能为空")
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
