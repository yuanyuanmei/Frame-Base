package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.api.dto.UavInfoDto;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 团队信息类
 */
public interface UavInfoService extends IService<UavInfoBean> {

    IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto);

    ResponseInfo saveUav(UavInfoDto uavInfoDto);
}
