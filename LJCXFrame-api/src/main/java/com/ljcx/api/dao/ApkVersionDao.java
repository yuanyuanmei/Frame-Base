package com.ljcx.api.dao;

import com.ljcx.api.beans.ApkVersionBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljcx.api.vo.ApkVersionVo;
/**
 * ljcx_apk_version
 * 
 * @author dm
 * @date 2019-11-29 12:05:14
 */

public interface ApkVersionDao extends BaseMapper<ApkVersionBean> {

    ApkVersionVo info();

}
