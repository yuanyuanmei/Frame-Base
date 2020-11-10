package com.ljcx.user.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
* AccountSysRoleUser 实体类
* Created by auto generator on Thu May 30 20:50:32 CST 2019.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "account_sys_role_user")
public class SysRoleUserBean {
        /**
        * 用户ID
        */
        private Long userId;
        /**
        * 角色ID
        */
        private Integer roleId;
}