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
* AccountSysPermission 实体类
* Created by auto generator on Fri May 31 19:34:58 CST 2019.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "account_sys_permission")
public class SysPermissionBean extends BaseEntity<Integer> {

        /**
        * 名称
        */
        private String name;
        /**
        * 类型 1菜单 2功能
        */
        private Integer type;

        /**
        * 菜单地址
        */
        private String url;
        /**
        * 菜单图标标识
        */
        private String icon;
        /**
        * 授权标识，多个以,分隔
        */
        private String permission;
        /**
        * 权限状态 1正常 2禁用
        */
        private Integer status;
        /**
        * 备注
        */
        private String memo;
        /**
        * 排序
        */
        private Integer sort;

        /**
         * 类型名称
         */
        @TableField(exist = false)
        private String typeName;

        /**
         * 父类ID
         */
        private Integer parentId;


        @TableField(exist = false)
        private List<SysPermissionBean> children;

        public SysPermissionBean(String name, String url) {
                this.name = name;
                this.url = url;
        }
}
