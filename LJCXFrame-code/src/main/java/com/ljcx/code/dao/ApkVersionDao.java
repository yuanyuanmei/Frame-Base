package com.ljcx.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.beans.ApkVersionBean;
import com.ljcx.code.dto.ApkVersionDto;
import com.ljcx.code.vo.ApkVersionVo;
import org.apache.ibatis.annotations.Param;

/**
 * ljcx_apk_version
 * 
 * @author dm
 * @date 2019-11-29 12:05:14
 */

public interface ApkVersionDao extends BaseMapper<ApkVersionBean> {

    ApkVersionVo info();

    IPage<ApkVersionBean> pageList(IPage<ApkVersionBean> page, @Param("item") ApkVersionDto apkVersionDto);
}
