package com.ljcx.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.code.beans.UavInfoBean;
import com.ljcx.code.dto.UavInfoDto;
import com.ljcx.code.vo.UavInfoVo;

import java.util.List;

/**
 * 团队信息类
 */
public interface UavInfoService extends IService<UavInfoBean> {

    IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto);

    List<UavInfoVo> equipUavList();
}
