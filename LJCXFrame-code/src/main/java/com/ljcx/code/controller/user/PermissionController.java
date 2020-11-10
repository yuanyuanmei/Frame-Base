package com.ljcx.code.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.SysPermissionDao;
import com.ljcx.user.dto.PermissionDto;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.service.SysPermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限相关接口
 *
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/permissions")
@Slf4j
public class PermissionController {

	@Autowired
	private SysPermissionDao permissionDao;

	@Autowired
	private SysPermissionService permissionService;

	/**
	 * 分页列表
	 * @param info
	 * info (roleDto 对象)
	 * @return
	 */
	@PostMapping("/pageList")
	@RequiresPermissions("sys:menu:query")
	public ResponseInfo pageList(@RequestBody String info) {
		PermissionDto permissionDto = JSONObject.parseObject(info, PermissionDto.class);
		return new ResponseInfo(permissionService.pageList(permissionDto));
	}

	/**
	 * 根据角色Id查询权限
	 * @param info
	 * @return
	 */
	@PostMapping("/queryByRoleId")
	@RequiresPermissions(value = { "sys:menu:query", "sys:role:query" }, logical = Logical.OR)
	@ValidateCustom(Integer.class)
	public ResponseInfo listByRoleId(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		return new ResponseInfo(permissionDao.getPermissionsByRoleId(jsonObject.getInteger("id"),""));
	}

	/**
	 * 根据角色Id查询权限
	 * @param info
	 * @return
	 */
	@PostMapping("/queryByUserId")
	@RequiresPermissions(value = { "sys:menu:query", "sys:role:query" }, logical = Logical.OR)
	@ValidateCustom(Integer.class)
	public ResponseInfo listByUserId(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		return new ResponseInfo(permissionDao.getPermissionsByUserId(jsonObject.getLong("id"),"all"));
	}

	/**
	 * 添加角色权限
	 * @param info
	 * @return
	 */
	@PostMapping("/saveRolePermission")
	@RequiresPermissions(value = { "sys:menu:add" }, logical = Logical.OR)
	public ResponseInfo saveRolePermission(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info, RoleDto.class);
		permissionService.saveRolePermission(roleDto);
		return new ResponseInfo().success("保存成功");
	}

	/**
	 * 获得树结构
	 * @param info
	 * @return
	 */
	@PostMapping("/treeList")
	@RequiresPermissions(value = { "sys:menu:query", "sys:role:query" }, logical = Logical.OR)
	public ResponseInfo treeList() {
		return new ResponseInfo(Arrays.asList(permissionService.treeList()));
	}



	/**
	 * 获得当前用户菜单栏
	 * @return
	 */
	@RequestMapping("/current")
	public ResponseInfo permissionsCurrent() {
		return new ResponseInfo(firstLevel());
	}

	public List<SysPermissionBean> firstLevel(){
		List<SysPermissionBean> list = UserUtil.getCurrentPermissions();
		if (list == null) {
			UserBaseBean user = UserUtil.getCurrentUser();
			list = permissionDao.getPermissionsByUserId(user.getId(),"ADMIN");
			UserUtil.setPermissions(list);
		}
		//获取菜单列表
		final List<SysPermissionBean> permissions = list.stream().filter(l -> l.getType().equals(2))
				.collect(Collectors.toList());

		//查询admin权限列表Id
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("permission","ADMIN");
		SysPermissionBean adminPermission = permissionDao.selectOne(wrapper);
		log.info("丹妹============》",adminPermission.getId());
		//获取一级菜单
		List<SysPermissionBean> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(adminPermission.getId())).collect(Collectors.toList());
		log.info("丹妹============》",firstLevel.toString());
		firstLevel.parallelStream().forEach(p -> {
			setChild(p, permissions);
		});
		return firstLevel;
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
