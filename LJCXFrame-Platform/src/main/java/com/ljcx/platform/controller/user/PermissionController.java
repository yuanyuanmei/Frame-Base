package com.ljcx.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.SysPermissionDao;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.service.SysPermissionService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限相关接口
 *
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

	@Autowired
	private IGenerator generator;

	@Autowired
	private SysPermissionDao permissionDao;

	@Autowired
	private SysPermissionService permissionService;

	/**
	 * 根据角色Id查询权限
	 * @param info
	 * @return
	 */
	@PostMapping("/queryByRoleId")
	//@RequiresPermissions(value = { "sys:menu:query", "sys:role:query" }, logical = Logical.OR)
	@ValidateCustom(Integer.class)
	public ResponseInfo listByRoleId(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		return new ResponseInfo(permissionDao.getPermissionsByRoleId(jsonObject.getInteger("id"),"PLATFORM"));
	}

	/**
	 * 根据角色Id查询权限
	 * @param info
	 * @return
	 */
	@PostMapping("/queryByUserId")
	//@RequiresPermissions(value = { "sys:menu:query", "sys:role:query" }, logical = Logical.OR)
	@ValidateCustom(Integer.class)
	public ResponseInfo listByUserId(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		return new ResponseInfo(permissionDao.getPermissionsByUserId(jsonObject.getLong("id"),"PLATFORM"));
	}

	/**
	 * 添加角色权限
	 * @param info
	 * @return
	 */
	@PostMapping("/saveRolePermission")
	//@RequiresPermissions(value = { "sys:menu:add" }, logical = Logical.OR)
	public ResponseInfo saveRolePermission(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info, RoleDto.class);
		permissionService.saveRolePermission(roleDto);
		return new ResponseInfo().success("保存成功");
	}


	/**
	 * 获得当前用户菜单栏
	 * @return
	 */
	@RequestMapping("/current")
	public ResponseInfo permissionsCurrent() {
		List<SysPermissionBean> list = UserUtil.getCurrentPermissions();
		if (list == null) {
			UserBaseBean user = UserUtil.getCurrentUser();
			list = permissionDao.getPermissionsByUserId(user.getId(),"PLATFORM");
			UserUtil.setPermissions(list);
		}
		final List<SysPermissionBean> permissions = list.stream().filter(l -> l.getType().equals(1))
				.collect(Collectors.toList());

		List<SysPermissionBean> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
		firstLevel.parallelStream().forEach(p -> {
			setChild(p, permissions);
		});

		return new ResponseInfo(firstLevel);
	}

	/**
	 * 设置子元素
	 * 2018.06.09
	 *
	 * @param p
	 * @param permissions
	 */
	private void setChild(SysPermissionBean p, List<SysPermissionBean> permissions) {
		List<SysPermissionBean> child = permissions.parallelStream().filter(a -> a.getParentId().equals(p.getId())).collect(Collectors.toList());
		p.setChildren(child);
		if (!CollectionUtils.isEmpty(child)) {
			child.parallelStream().forEach(c -> {
				//递归设置子元素，多级菜单支持
				setChild(c, permissions);
			});
		}
	}

}
