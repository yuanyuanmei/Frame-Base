package com.ljcx.user.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


/**
 * AccountSysRole 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "account_sys_role")
public class SysRoleBean extends BaseEntity<Integer> {

        /**
         * 角色名称
         */
        private String name;
        /**
         * 描述
         */
        private String memo;

        /**
         * 序号
         */
        private Integer sort;

        /**
         * 菜单集合
         */
        @TableField(exist = false)
        private List<SysPermissionBean> permissionBeans;
}
