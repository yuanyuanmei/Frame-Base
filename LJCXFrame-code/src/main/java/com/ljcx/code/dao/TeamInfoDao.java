package com.ljcx.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.beans.TeamInfoBean;
import com.ljcx.code.dto.TeamInfoDto;
import com.ljcx.code.vo.EqumentCountVo;
import com.ljcx.code.vo.TeamInfoVo;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TeamInfoDao extends BaseMapper<TeamInfoBean> {


    IPage<TeamInfoVo> pageList(IPage<TeamInfoVo> page, @Param("item") TeamInfoDto teamInfoDto, @Param("currentUser") UserBaseBean userBaseBean);

    List<TeamInfoVo> pageList(@Param("item") TeamInfoDto teamInfoDto, @Param("currentUser") UserBaseBean userBaseBean);

    List<Long> teamIdsByUserId(@Param("userId") Long userId);

    List<Long> teamDownList(@Param("teamId") Long teamId);

    List<EqumentCountVo> equmentCount(@Param("teamIds") List<Long> teamIds);

    int delBatchIds(@Param("list") List<Long> ids);

    int bindTeam(@Param("item") TeamInfoDto teamInfoDto);

    int cancelTeam(@Param("item") TeamInfoDto teamInfoDto);

    int delRelationShipByMid(@Param("mId") Long mId, @Param("mType") int mType);

    int teamEqumentCount(@Param("teamId") Long teamId);

}