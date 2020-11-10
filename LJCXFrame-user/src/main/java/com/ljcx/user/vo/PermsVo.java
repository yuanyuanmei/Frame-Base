package com.ljcx.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.user.beans.SysPermissionBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PermsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 名称
     */
    private String name;

    private String label;
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
    private String typeName;

    /**
     * 父类ID
     */
    private Integer parentId;


    private List<PermsVo> children;

    public String getLabel() {
        return name;
    }
}
