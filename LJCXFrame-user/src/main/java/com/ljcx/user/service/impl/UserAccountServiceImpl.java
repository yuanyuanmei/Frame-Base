package com.ljcx.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMAccountService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.common.utils.IDUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.user.beans.UserAccountBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.dao.SysRoleDao;
import com.ljcx.user.dao.UserAccountDao;
import com.ljcx.user.dao.UserBaseDao;
import com.ljcx.user.dto.AccountDto;
import com.ljcx.user.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountDao, UserAccountBean> implements UserAccountService {

    @Autowired
    private UserAccountDao accountDao;

    @Autowired
    private UserBaseDao baseDao;

    @Autowired
    private SysRoleDao roleDao;

    @Autowired
    private IGenerator generator;

    @Autowired
    private IMAccountService imAccountService;

    @Override
    public String passwordEncoder(String credentials, String salt) {
        Object object = new SimpleHash("MD5", credentials, salt, UserConstants.HASH_ITERATIONS);
        return object.toString();
    }

    @Override
    public UserAccountBean getAccount(String account) {
        return accountDao.findByAccount(account);
    }

    @Transactional
    @Override
    public ResponseInfo save(AccountDto accountDto) {
        int result = 0;
        String msg = "";
        UserAccountBean accountBean = generator.convert(accountDto, UserAccountBean.class);
        if(Objects.isNull(accountBean.getId())){
            //1.生成7位数随机用户名
            String username = IDUtils.getItemID(7);
            UserBaseBean baseBean = generator.convert(accountDto, UserBaseBean.class);
            baseBean.setUsername(username);
            //2.新增用户基础表
            baseDao.insert(baseBean);
            //3.获取usr_id
            accountBean.setUserId(baseBean.getId());
            //4.密码加盐,md5加密
            String salt = IDUtils.getItemID(4);
            accountBean.setSalt(salt);
            accountBean.setPassword(passwordEncoder(accountBean.getPassword(), accountBean.getSalt()));
            //5.新增用户帐号表
            result = accountDao.insert(accountBean);
            //6.新增角色关系表
            if(accountDto.getRoleIds() != null && accountDto.getRoleIds().size() > 0 && accountDto.getRoleIds().get(0) != null){
                roleDao.saveUserRole(baseBean.getId(),accountDto.getRoleIds());
            }

            //7.新增IM通讯
            JSONObject data = new JSONObject();
            data.put("Identifier",baseBean.getUsername());
            data.put("Nick",baseBean.getNickname());
            data.put("FaceUrl","http://www.qq.com");
            IMResponse imResponse = imAccountService.accountImport(data);
            if(imResponse.getActionStatus().equals("OK")){
                baseBean.setImRegistered(1);
                baseDao.updateById(baseBean);
            }
            msg = "新增成功";
        }else{
            result = accountDao.updateById(accountBean);
            msg = "更新成功";
        }
        if(result > 0){
            return new ResponseInfo(accountBean);
        }
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(),"保存失败");
    }

    @Override
    public IPage<UserAccountBean> list(Page<UserAccountBean> page) {
        return accountDao.list(page);
    }

    /**
     * @todo 将salt保存到数据库或者缓存中
     * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
     */
    @Override
    public String generateJwtToken(String username) {
        UserAccountBean account = accountDao.findByAccount(username);
        return ""; //生成jwt token，设置过期时间为1小时
    }

    @Override
    public ResponseInfo updatePwd(String info,Long userId) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        UserAccountBean account = accountDao.selectOne(wrapper);
        account.setPassword(passwordEncoder(jsonObject.getString("password"), account.getSalt()));
        accountDao.updateById(account);
        return new ResponseInfo().success("修改成功");
    }

}
