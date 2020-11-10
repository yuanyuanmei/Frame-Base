package com.ljcx.code.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.dao.SysRoleDao;
import com.ljcx.user.dto.RoleDto;
import com.ljcx.user.service.SysRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色相关接口
 *
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

	@Autowired
	private SysRoleService roleService;

	@Autowired
	private SysRoleDao roleDao;

	@Autowired
	private IGenerator generator;



	/**
	 * 保存角色
	 * @param info
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:role:add")
	@ValidateCustom(RoleDto.class)
	public ResponseInfo save(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info, RoleDto.class);
		roleService.saveRole(roleDto);
		return new ResponseInfo().success("保存成功");
	}


	/**
	 * 获取角色信息
	 * @param info
	 * @return
	 */
	@PostMapping("/get")
	@RequiresPermissions("sys:role:query")
	@ValidateCustom(Integer.class)
	public ResponseInfo query(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		return new ResponseInfo(roleDao.selectByPrimaryKey(jsonObject.getInteger("id")));
	}

	/**
	 * 角色树结构
	 * @return
	 */
	@PostMapping("/treeList")
	@RequiresPermissions("sys:role:query")
	public ResponseInfo treeList() {
		RoleDto roleDto = new RoleDto();
		List<SysRoleBean> roleBeanList = roleDao.list(roleDto);
		List<BaseTree> children = new ArrayList<>();
		roleBeanList.stream().forEach(roleBean->{
			BaseTree tree = generator.convert(roleBean, BaseTree.class);
			tree.setAttr(roleBean);
			children.add(tree);
		});
		BaseTree baseTree = new BaseTree(-1,"角色列表",true,null,0,0,children);
		return new ResponseInfo(baseTree);
	}

	/**
	 * 获取角色分页列表
	 * @param info
	 * info (roleDto 对象)
	 * @return
	 */
	@PostMapping("/pageList")
	@RequiresPermissions("sys:role:query")
	public ResponseInfo pageList(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info, RoleDto.class);
		return new ResponseInfo(roleService.pageList(roleDto));
	}

	/**
	 * 根据ID删除
	 * @param info
	 * @return
	 */
	@PostMapping("/del")
	@RequiresPermissions("sys:role:del")
	@ValidateCustom(Integer.class)
	public ResponseInfo delete(@RequestBody String info) {
		JSONObject jsonObject = JSONObject.parseObject(info);
		Integer id = jsonObject.getInteger("id");
		return roleService.del(id);
	}

	/**
	 * 添加角色下的用户
	 * @param info
	 * @return
	 */
	@PostMapping("/addRoleUser")
	@RequiresPermissions("sys:role:add")
	public ResponseInfo addRoleUser(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info,RoleDto.class);
		roleDao.saveRoleUser(roleDto.getId(),roleDto.getUserIds());
		return new ResponseInfo(new ResponseInfo().success("添加成功"));
	}

	/**
	 * 删除角色下的用户
	 * @param info
	 * @return
	 */
	@PostMapping("/delRoleUser")
	@RequiresPermissions("sys:role:del")
	public ResponseInfo delRoleUser(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info,RoleDto.class);
		roleDao.delRoleUser(roleDto.getId(),roleDto.getUserIds());
		return new ResponseInfo(new ResponseInfo().success("删除成功"));
	}

	/**
	 * 获取用户下角色
	 * @param info
	 * @return
	 */
	@PostMapping("/userRoleList")
	public ResponseInfo userRoleList(@RequestBody String info) {
		RoleDto roleDto = JSONObject.parseObject(info,RoleDto.class);
		return new ResponseInfo(roleDao.userRoleList(roleDto.getUserId()));
	}
}
