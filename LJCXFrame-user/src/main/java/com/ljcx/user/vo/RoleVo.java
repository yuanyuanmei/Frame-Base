package com.ljcx.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.user.beans.SysPermissionBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

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

    private int isChecked;

    private List<RoleVo> children;
}
