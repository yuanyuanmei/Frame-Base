package com.ljcx.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.SysPermissionDao;
import com.ljcx.user.dao.SysRoleDao;
import com.ljcx.user.dao.UserBaseDao;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.dto.UserDto;
import com.ljcx.user.service.SysRoleService;
import com.ljcx.user.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleBean> implements SysRoleService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SysRoleDao roleDao;

    @Autowired
    private UserBaseDao baseDao;

    @Autowired
    private SysPermissionDao permissionDao;


    @Transactional
    @Override
    public int saveRole(RoleDto roleDto) {
        SysRoleBean roleBean = generator.convert(roleDto, SysRoleBean.class);

        if (roleBean.getId() != null) {// 修改
            SysRoleBean r = roleDao.getRole(roleBean.getName());
            if (r != null && r.getId() != roleBean.getId()) {
                throw new IllegalArgumentException(roleBean.getName() + "已存在");
            }

            roleDao.updateById(roleBean);
        } else {// 新增
            SysRoleBean r = roleDao.getRole(roleBean.getName());
            if (r != null) {
                throw new IllegalArgumentException(roleBean.getName() + "已存在");
            }

            roleDao.insert(roleBean);
        }

        if(roleDto.getPermissionIds() != null){
            permissionDao.delPermissionsByRoleId(roleBean.getId());
            roleDao.saveRolePermission(roleBean.getId(),roleDto.getPermissionIds());
        }
        return 1;
    }

    private void saveRolePermission(Integer roleId, List<Integer> permissionIds) {
        roleDao.delRolePermission(roleId);
        permissionIds.remove(0L);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            roleDao.saveRolePermission(roleId, permissionIds);
        }
    }

    @Override
    public IPage<SysRoleBean> pageList(RoleDto roleDto) {
        IPage<SysRoleBean> page = new Page<>();
        page.setCurrent(roleDto.getPageNum());
        page.setSize(roleDto.getPageSize());
        return roleDao.list(page,roleDto);
    }

    @Override
    public IPage<RoleVo> listVo(RoleDto roleDto) {
        IPage<RoleVo> page = new Page<>();
        page.setCurrent(roleDto.getPageNum());
        page.setSize(roleDto.getPageSize());
        IPage<RoleVo> roleVoIPage = roleDao.listVo(page, roleDto);
        roleVoIPage.getRecords().stream().forEach(role -> {
            List<RoleVo> convert = generator.convert(role.getChildren(), RoleVo.class);
            role.setChildren(convert);
        });
        return roleVoIPage;
    }

    @Override
    public ResponseInfo del(Integer id) {
        //1.查询角色下是否关联用户
        UserDto userDto = new UserDto();
        userDto.setRoleId(id);
        IPage page = new Page();
        IPage userByRole = baseDao.pageList(page, userDto);
        if(userByRole.getTotal()>0){
            return new ResponseInfo(new ResponseInfo().failed("请先删除角色下的用户"));
        }
        roleDao.deleteById(id);
        return new ResponseInfo(new ResponseInfo().success("删除成功"));
    }

}
