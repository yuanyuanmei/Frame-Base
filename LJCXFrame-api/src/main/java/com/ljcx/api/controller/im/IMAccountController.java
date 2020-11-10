package com.ljcx.api.controller.im;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.im.dto.IMAccountDto;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMAccountService;
import com.ljcx.framework.live.common.profile.AppProfile;
import com.ljcx.framework.live.tencent.LiveUtil;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.service.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/im/account")
public class IMAccountController {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private IMAccountService imAccountService;

    @PostMapping("/userSig")
    public ResponseInfo userSig(@RequestBody String info, HttpServletRequest request){
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getInteger("isFail") == null){
            return new ResponseInfo().failed("isFail发送状态不能为空");
        }
        Long userId = JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN));
        UserBaseBean baseBean = getUserSign(jsonObject.getInteger("isFail"), userId);
        JSONObject resp = new JSONObject();
        resp.put("userSig",baseBean.getUserSig());
        resp.put("username",baseBean.getUsername());
        return new ResponseInfo(resp);
    }

    @SuppressWarnings("all")
    private UserBaseBean getUserSign(Integer isFail,Long userId){
        UserBaseBean baseBean = userBaseService.getById(userId);
        //判断签名是否为空并且是否过期
        if(isFail == 1
                || StringUtils.isEmpty(baseBean.getUserSig())
                || System.currentTimeMillis() > baseBean.getSignExpireTime().getTime()){
            String sign = LiveUtil.genSig(baseBean.getUsername());
            baseBean.setUserSig(sign);
            baseBean.setSignExpireTime(new Date(System.currentTimeMillis()+ AppProfile.SIGN_EXPIRE_TIME*1000));
            userBaseService.updateById(baseBean);
        }
        return baseBean;
    }
    @PostMapping("/import")
    public ResponseInfo accountImport(HttpServletRequest request) {
        Long userId = JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN));
        UserBaseBean user = userBaseService.getById(userId);
        if(user.getImRegistered() == 1){
            return new ResponseInfo().failed("该账号已注册");
        }
        JSONObject data = new JSONObject();
        data.put("Identifier",user.getUsername());
        data.put("Nick",user.getNickname());
        data.put("FaceUrl","http://www.qq.com");
        IMResponse imResponse = imAccountService.accountImport(data);
        if(imResponse.getActionStatus().equals("OK")){
            user.setImRegistered(1);
            userBaseService.updateById(user);
            return new ResponseInfo().success("注册成功");
        }
        return new ResponseInfo(imResponse);
    }

    @PostMapping("/batch")
    public ResponseInfo accountBatch(@RequestBody String info) {
        IMAccountDto imAccountDto = JSONObject.parseObject(info, IMAccountDto.class);
        IMResponse imResponse = imAccountService.accountBatch(imAccountDto);
        if(imResponse.getActionStatus().equals("OK")){
            if(imAccountDto.getOpt().equals("delete")){
                userBaseService.deleteImAccount(Arrays.asList(imAccountDto.getUsernames()));
            }
            return new ResponseInfo().success("操作成功");
        }
        return new ResponseInfo(imResponse);
    }

}
