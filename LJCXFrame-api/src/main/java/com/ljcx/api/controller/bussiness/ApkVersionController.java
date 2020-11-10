package com.ljcx.api.controller.bussiness;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.ljcx.framework.sys.service.IGenerator;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.api.service.ApkVersionService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.api.dto.ApkVersionDto;

/**
 * ljcx_apk_version
 *
 * @author dm
 * @date 2019-11-29 12:05:14
 */
@RestController
@RequestMapping("/apk")
public class ApkVersionController {
    @Autowired
    private ApkVersionService apkVersionService;

    @Autowired
    private IGenerator generator;

    @PostMapping("/info")
    //@RequiresPermissions("sys:apk:query")
    public ResponseInfo info() {
        return new ResponseInfo(apkVersionService.info());
    }


}
