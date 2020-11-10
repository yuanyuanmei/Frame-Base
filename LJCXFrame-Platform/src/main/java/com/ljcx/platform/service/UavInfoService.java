package com.ljcx.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.platform.dto.UavInfoDto;
import com.ljcx.platform.mgbean.UavInfoMongo;
import com.ljcx.platform.vo.UavInfoVo;

/**
 * 团队信息类
 */
public interface UavInfoService extends IService<UavInfoBean> {

    IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto);

    ResponseInfo getState(Long teamId);

    UavInfoVo planPanel(UavInfoDto uavInfoDto);

    ResponseInfo saveRoutes(UavInfoVo uavInfoVo);

    ResponseInfo findRoutes(UavInfoMongo uavInfoMongo);
}
