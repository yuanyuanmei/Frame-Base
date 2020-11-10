package com.ljcx.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.vo.RoleVo;

/**
 * 角色业务处理
 */
public interface SysRoleService extends IService<SysRoleBean> {

    int saveRole(RoleDto roleDto);

    IPage<SysRoleBean> pageList(RoleDto roleDto);

    IPage<RoleVo> listVo(RoleDto roleDto);

    ResponseInfo del(Integer id);

}
