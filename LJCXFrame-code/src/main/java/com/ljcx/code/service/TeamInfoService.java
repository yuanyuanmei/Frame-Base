package com.ljcx.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.code.beans.TeamInfoBean;
import com.ljcx.code.dto.TeamInfoDto;
import com.ljcx.code.vo.EqumentCountVo;
import com.ljcx.code.vo.TeamInfoVo;
import com.ljcx.common.utils.ResponseInfo;

import java.util.List;

/**
 * 团队信息类
 */
public interface TeamInfoService extends IService<TeamInfoBean> {

    IPage<TeamInfoVo> pageList(TeamInfoDto teamInfoDto);

    List<TeamInfoVo> list(TeamInfoDto teamInfoDto);

    ResponseInfo saveTeamInfo(TeamInfoDto teamInfoDto);

    List<TeamInfoVo> treeList(TeamInfoDto teamInfoDto);

    ResponseInfo bindTeam(TeamInfoDto teamInfoDto);

    ResponseInfo cancelTeam(TeamInfoDto teamInfoDto);

    TeamInfoVo equipmentList(Long id);

    List<EqumentCountVo> equipmentCount(List<Long> teamIds);

    List<Long> userTeamList();

    int delRelationShipByMid(Long mId, int mType);

    ResponseInfo delTeam(TeamInfoDto teamInfoDto);
}
