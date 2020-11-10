package com.ljcx.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.constants.UserConstants;
import com.ljcx.user.dto.UserDto;
import com.ljcx.user.service.UserAccountService;
import com.ljcx.user.service.UserBaseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户controller
 */
@Api(value = "用户模块")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserBaseController {

    @Autowired
    private UserBaseService baseService;
    

    /**
     * 分页查询用户列表
     * @param info
     * info(userDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    //@RequiresPermissions("sys:user:query")
    public ResponseInfo pageList(@RequestBody String info) {
        UserDto userDto = JSONObject.parseObject(info, UserDto.class);
        return new ResponseInfo(baseService.pageList(userDto));
    }

    /**
     * 修改用户信息
     * @param info
     * info(userDto 对象)
     * @return
     */
    @PostMapping("/update")
    //@RequiresPermissions("sys:user:update")
    public ResponseInfo update(@RequestBody String info, HttpServletRequest request) {
        UserDto userDto = JSONObject.parseObject(info, UserDto.class);
        userDto.setId(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return baseService.updateByDto(userDto);
    }

    /**
     * 批量启用禁用用户
     * @param info
     * info(userDto 对象)
     * @return
     */
    @PostMapping("/updateStatus")
    //@RequiresPermissions("sys:user:update")
    public ResponseInfo updateStatus(@RequestBody String info) {
        UserDto userDto = JSONObject.parseObject(info, UserDto.class);
        return baseService.updateStatus(userDto);
    }

    /**
     * 获得用户列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("sys:user:query")
    public ResponseInfo list() {
        return new ResponseInfo(baseService.list());
    }

    /**
     * 获得用户信息
     */
    @PostMapping("/info")
    //@RequiresPermissions("sys:user:query")
    public ResponseInfo info(HttpServletRequest request) {
        return new ResponseInfo(baseService.info(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN))));
    }


}
