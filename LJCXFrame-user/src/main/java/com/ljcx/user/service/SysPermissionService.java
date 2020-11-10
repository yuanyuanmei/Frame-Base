package com.ljcx.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.base.BaseTree;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.dto.PermissionDto;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.vo.PermsVo;

import java.util.List;


/**
 * 权限业务处理
 */
public interface SysPermissionService extends IService<SysPermissionBean> {

    IPage<SysPermissionBean> pageList(PermissionDto permissionDto);

    int saveRolePermission(RoleDto roleDto);

    PermsVo treeList();

}
