package com.ljcx.user.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
* AccountSysPermission 实体类
* Created by auto generator on Fri May 31 19:34:58 CST 2019.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@NotNull(message = "权限参数对象不能为空")
public class PermissionDto extends PageDto {

        private Integer id;
        /**
        * 名称
        */
        @NotBlank(message = "权限名称不能为空")
        private String name;
        /**
        * 类型 1菜单 2功能
        */
        @NotNull(message = "权限类型不能为空")
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
         * 父类ID
         */
        @NotNull(message = "上级ID不能为空")
        private Integer parentId;

}
