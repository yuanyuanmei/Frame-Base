package com.ljcx.panor.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.panor.utils.WechatUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.ljcx.framework.sys.service.IGenerator;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.panor.service.BaseUserService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.panor.dto.BaseUserDto;
import com.ljcx.panor.beans.BaseUserBean;

/**
 * 用户基础表
 *
 * @author dm
 * @date 2020-05-27 10:12:59
 */
@RestController
@RequestMapping("/panor/baseuser")
public class BaseUserController {
    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private IGenerator generator;

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/register")
    @ValidateCustom(BaseUserDto.class)
    public ResponseInfo register(@RequestBody String info){
        BaseUserDto baseUserDto = JSONObject.parseObject(info, BaseUserDto.class);
        BaseUserBean bean = generator.convert(baseUserDto, BaseUserBean.class);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_account",baseUserDto.getUserAccount());
        int count = baseUserService.count(wrapper);
        if(count == 0){
            baseUserService.save(bean);
        }else{
            baseUserService.update(bean,wrapper);
        }
        return new ResponseInfo().success("保存成功");
    }

    /**
     * 获取微信小程序用户唯一OpenId
     * @param info
     * @return
     */
    @PostMapping("/getUserId")
    public ResponseInfo getWXOpenId(@RequestBody String info){
        Integer userId = 0;
        String jscode = JSONObject.parseObject(info).getString("code");
        String openId = WechatUtil.getOpenId(jscode);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_account",openId);
        BaseUserBean bean = baseUserService.getOne(wrapper);
        if(bean == null){
            BaseUserBean baseUserBean = new BaseUserBean();
            baseUserBean.setUserAccount(openId);
            baseUserService.save(baseUserBean);
            userId = baseUserBean.getUserId();
        }else{
            userId = bean.getUserId();
        }
        return new ResponseInfo(userId);
    }



}
