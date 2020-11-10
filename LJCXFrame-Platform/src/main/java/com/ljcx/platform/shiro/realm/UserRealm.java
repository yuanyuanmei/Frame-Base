package com.ljcx.platform.shiro.realm;

import com.ljcx.common.utils.spring.SpringUtil;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.user.beans.SysPermissionBean;
import com.ljcx.user.beans.SysRoleBean;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.dao.SysPermissionDao;
import com.ljcx.user.dao.SysRoleDao;
import com.ljcx.user.dao.UserAccountDao;
import com.ljcx.user.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 从 Realm 得到用户相应的角色/权限进行验证用户是否能进行操作,可以把 Realm 看成 DataSource，即安全数据源。
 * 从数据库中获取认证信息及授权信息
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    private UserUtil userUtil;

    public UserRealm(UserUtil userUtil){
        this.userUtil = userUtil;
    }
    /**
     * 授权验证
     *
     * @param principals 认证人
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //shiro中类如果用autowired注入的话，在后面的使用中将不能被切面
//        UserAccountDao userDao = SpringUtil.getBean(UserAccountDao.class);
//
//        SysPermissionDao permissionDao = SpringUtil.getBean(SysPermissionDao.class);
//
//        SysRoleDao roleDao = SpringUtil.getBean(SysRoleDao.class);
//
//        UserAccountBean paramBean = (UserAccountBean) principals.getPrimaryPrincipal();
//
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        UserAccountBean account = userDao.findByAccount(paramBean.getAccount());
//
//        //角色名称集合
//        List<SysRoleBean> roleBeans = roleDao.getRolesByUserId(account.getUserBaseBean().getId());
//        Set<String> roleNames = roleBeans.stream().map(SysRoleBean::getName).collect(Collectors.toSet());
//        authorizationInfo.setRoles(roleNames);
//
//        //权限名称集合
//        List<SysPermissionBean> permissionBeans = permissionDao.getPermissionsByUserId(account.getUserBaseBean().getId());
//        Set<String> permissionNames = permissionBeans.stream()
//                .filter(item -> item.getType() > 1)
//                .map(SysPermissionBean::getPermission).collect(Collectors.toSet());
//        authorizationInfo.setStringPermissions(permissionNames);
//
//        userUtil.setPermissions(permissionBeans);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }


    /**
     * 用户认证
     *
     * @param token 令牌
     * @return 认证信息
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UserAccountDao userDao = SpringUtil.getBean(UserAccountDao.class);

        UserAccountService accountService = SpringUtil.getBean(UserAccountService.class);

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();

        UserAccountBean user = userDao.findByAccount(username);

        if (user == null) {
            throw new UnknownAccountException("该账号未注册");//没找到帐号
        }

        if (user.getUserBaseBean().getStatus() == 2) {
            throw new IncorrectCredentialsException("账号被禁用，请联系管理员");
        }

        //暂时不加锁定账号功能
//        if (user.getLockTime() != null && user.getLockTime().after(new Date())) {
//            throw new LockedAccountException(); //帐号锁定
//        }

        if (!user.getPassword()
                .equals(accountService.passwordEncoder(new String(usernamePasswordToken.getPassword()), user.getSalt()))) {
            throw new IncorrectCredentialsException("账号密码错误");
        }

        //获取登录权限
        SysPermissionDao permissionDao = SpringUtil.getBean(SysPermissionDao.class);
        List<SysPermissionBean> permissions = permissionDao.getPermissionsByUserId(user.getUserBaseBean().getId(),"PLATFORM");
        if (permissions.size() == 0) {
            throw new IncorrectCredentialsException("账号没有平台登录权限，请联系管理员");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());

        userUtil.setUser(user.getUserBaseBean());
        return authenticationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

}