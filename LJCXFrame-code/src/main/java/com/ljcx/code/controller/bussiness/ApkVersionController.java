package com.ljcx.code.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.code.dto.ApkVersionDto;
import com.ljcx.code.service.ApkVersionService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 查询版本信息
     * @return
     */
    @PostMapping("/info")
    @RequiresPermissions("sys:apk:query")
    public ResponseInfo info() {
        return new ResponseInfo(apkVersionService.info());
    }

    /**
     * 分页查询列表
     * @param info
     * info(carInfoDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("sys:apk:query")
    public ResponseInfo pageList(@RequestBody String info) {
        ApkVersionDto apkVersionDto = JSONObject.parseObject(info, ApkVersionDto.class);
        return new ResponseInfo(apkVersionService.pageList(apkVersionDto));
    }

    /**
     * 查询版本信息
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:apk:update")
    public ResponseInfo update(@RequestBody String info) {
        ApkVersionDto apkVersionDto = JSONObject.parseObject(info, ApkVersionDto.class);
        return apkVersionService.updateApk(apkVersionDto);
    }

    /**
     * 上传版本附件
     * @return
     */
    @PostMapping("/upload")
    @RequiresPermissions("sys:apk:add")
    public ResponseInfo upload(MultipartFile file) throws Exception {
        return apkVersionService.upload(file);
    }



}
