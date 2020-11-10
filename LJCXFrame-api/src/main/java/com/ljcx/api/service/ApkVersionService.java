package com.ljcx.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.ApkVersionBean;
import com.ljcx.api.vo.ApkVersionVo;


/**
 * ljcx_apk_version
 *
 * @author dm
 * @date 2019-11-29 12:05:14
 */
public interface ApkVersionService extends IService<ApkVersionBean> {

    ApkVersionVo info();
}

