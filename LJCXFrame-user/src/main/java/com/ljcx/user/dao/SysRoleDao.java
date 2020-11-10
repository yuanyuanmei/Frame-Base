package com.ljcx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDao extends BaseMapper<SysRoleBean> {

    /**
     * 根据用户ID查询角色集合
     * @param userId
     * @return
     */
    List<SysRoleBean> getRolesByUserId(@Param("userId") Long userId);

    SysRoleBean getRole(String name);

    SysRoleBean selectByPrimaryKey(Integer id);

    int delRolePermission(Integer roleId);

    int delUserRole(Long userId);

    int saveRolePermission(@Param("roleId") Integer roleId,@Param("permissionIds") List<Integer> permissionIds);

    int saveUserRole(@Param("userId") Long userId,@Param("roleIds") List<Integer> roleIds);

    int saveRoleUser(@Param("roleId") Integer roleId,@Param("userIds") List<Long> userIds);

    List<SysRoleBean> list(@Param("item") RoleDto roleDto);

    IPage<SysRoleBean> list(IPage<SysRoleBean> page,@Param("item") RoleDto roleDto);

    IPage<RoleVo> listVo(IPage<RoleVo> page,@Param("item") RoleDto roleDto);

    int delRoleUser(@Param("roleId") Integer roleId,@Param("userIds") List<Long> userIds);

    List<RoleVo> userRoleList(@Param("userId") Long userId);
}