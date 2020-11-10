package com.ljcx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.dto.PermissionDto;
import com.ljcx.user.vo.PermsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限接口
 */
public interface SysPermissionDao extends BaseMapper<SysPermissionBean> {


    /**
     * 角色分页列表
     */
    IPage<SysPermissionBean> pageList(IPage<SysPermissionBean> page, @Param("item") PermissionDto permissionDto);

    /**
     * 根据角色ID查询权限集合
     */
    List<SysPermissionBean> getPermissionsByRoleId(@Param("roleId") Integer roleId,@Param("module") String module);

    /**
     * 根据用户ID查询权限集合
     */
    List<SysPermissionBean> getPermissionsByUserId(@Param("userId") Long userId,@Param("module") String module);

    /**
     * 根据角色ID删除权限
     */
    int delPermissionsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取权限列表
     */
    List<PermsVo> list();


}