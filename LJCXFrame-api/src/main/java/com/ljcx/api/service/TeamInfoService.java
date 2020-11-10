package com.ljcx.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.TeamInfoBean;
import com.ljcx.api.dto.TeamInfoDto;
import com.ljcx.api.vo.TeamInfoVo;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;

import java.util.List;

/**
 * 团队信息类
 */
public interface TeamInfoService extends IService<TeamInfoBean> {

    List<TeamInfoVo> list(Long userId);

    TeamInfoVo info(Long id,Long userId);

    ResponseInfo getDataNums(Long teamId, Long userId);

    int getTotalNums(Long teamId, Long userId);

}
