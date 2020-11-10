package com.ljcx.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.TeamInfoBean;
import com.ljcx.platform.dto.TeamInfoDto;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TeamInfoVo;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;

import java.util.List;

/**
 * 团队信息类
 */
public interface TeamInfoService extends IService<TeamInfoBean> {

    List<TeamInfoVo> treeList(Long userId);

    List<TeamInfoVo> list(Long userId);

    TeamInfoVo info(Long id);

    List<MapVo> getDataNums(Long teamId);

    int getTotalNums(Long teamId);
}
