
package com.ljcx.framework.sys.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dic")
public class SysDicBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	//编码
	private String code;
	//名称
	private String name;
	//备注
	private String memo;
	//排序
	private Integer sort;
	//创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	//创建用户
	private Long createUser;
}
