package com.ljcx.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMAccountService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.SysRoleDao;
import com.ljcx.user.dao.UserAccountDao;
import com.ljcx.user.dao.UserBaseDao;
import com.ljcx.user.dto.UserDto;
import com.ljcx.user.service.UserBaseService;
import com.ljcx.user.vo.MemberVo;
import com.ljcx.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户基础业务实现类
 */
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseDao, UserBaseBean> implements UserBaseService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UserBaseDao baseDao;

    @Autowired
    private SysRoleDao roleDao;

    @Autowired
    private UserAccountDao accountDao;

    @Autowired
    private IMAccountService imAccountService;

    @Override
    public IPage<UserBaseBean> pageList(UserDto userDto) {
        IPage<UserBaseBean> page = new Page<>();
        page.setCurrent(userDto.getPageNum());
        page.setSize(userDto.getPageSize());
        return baseDao.pageList(page,userDto);
    }

    @Override
    public ResponseInfo updateByDto(UserDto userDto) {
        //修改用户基础表
        UserBaseBean baseBean = generator.convert(userDto,UserBaseBean.class);
        baseDao.updateById(baseBean);
        //修改用户角色
        if(userDto.getRoleIds() != null && userDto.getRoleIds().size() > 0){
            roleDao.delUserRole(baseBean.getId());
            roleDao.saveUserRole(baseBean.getId(),userDto.getRoleIds());
        }
        //7.新增IM通讯
        UserBaseBean user = baseDao.selectById(baseBean.getId());
        if(user.getImRegistered() == 0){
            JSONObject data = new JSONObject();
            data.put("Identifier",user.getUsername());
            data.put("Nick",user.getNickname());
            data.put("FaceUrl","http://www.qq.com");
            IMResponse imResponse = imAccountService.accountImport(data);
            if(imResponse.getActionStatus().equals("OK")){
                user.setImRegistered(1);
                baseDao.updateById(user);
            }
        }
        return new ResponseInfo().success("修改成功");
    }

    @Override
    public ResponseInfo updateStatus(UserDto userDto) {
        baseDao.updateStatus(userDto);
        return new ResponseInfo().success("修改成功");
    }

    @Override
    public UserVo info(Long userId) {
        UserBaseBean baseBean = baseDao.selectById(userId);
        UserVo userVo = generator.convert(baseBean, UserVo.class);
        List<SysRoleBean> roleList = roleDao.getRolesByUserId(userId);
        List<String> roleNames = roleList.stream().map(roleBean -> {
            return roleBean.getName();
        }).collect(Collectors.toList());
        userVo.setRoleName(roleNames);
        return userVo;
    }

    @Override
    public List<MemberVo> listByTeamId(Long teamId) {
        return baseDao.listByTeamId(teamId);
    }

    @Override
    public List<MemberVo> listUpTeamByTeamId(Long teamId) {
        return baseDao.listUpTeamByTeamId(teamId);
    }

    @Override
    public int deleteImAccount(List<String> usernames) {
        return baseDao.deleteImAccount(usernames);
    }

    @Override
    public ResponseInfo del(Long id) {
        UserBaseBean baseBean = baseDao.selectById(id);
        if(baseBean == null){
            return new ResponseInfo().failed("该账号不存在");
        }
        QueryWrapper delWrapper = new QueryWrapper();
        delWrapper.eq("user_id",baseBean.getId());
        //删除团队关系表

        //删除账号表
        accountDao.delete(delWrapper);
        //删除角色表
        roleDao.delUserRole(baseBean.getId());
        //删除主表
        baseDao.deleteById(id);
        return new ResponseInfo().success("删除成功");
    }


}

