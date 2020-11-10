package com.ljcx.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.code.beans.ApkVersionBean;
import com.ljcx.code.dto.ApkVersionDto;
import com.ljcx.code.vo.ApkVersionVo;
import com.ljcx.common.utils.ResponseInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * ljcx_apk_version
 *
 * @author dm
 * @date 2019-11-29 12:05:14
 */
public interface ApkVersionService extends IService<ApkVersionBean> {

    ApkVersionVo info();

    IPage<ApkVersionBean> pageList(ApkVersionDto apkVersionDto);

    ResponseInfo updateApk(ApkVersionDto apkVersionDto);

    ResponseInfo upload(MultipartFile file) throws Exception;
}

