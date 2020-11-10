package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljcx.api.beans.TeamInfoBean;
import com.ljcx.api.dto.TeamInfoDto;
import com.ljcx.api.vo.MapVo;
import com.ljcx.api.vo.TeamInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TeamInfoDao extends BaseMapper<TeamInfoBean> {


    List<TeamInfoVo> list(@Param("item") TeamInfoDto teamInfoDto);

    List<TeamInfoVo> teamList(@Param("item") TeamInfoDto teamInfoDto);

    TeamInfoVo info(@Param("id") Long id);

    List<MapVo> getDataNums(@Param("teamId") Long teamId, @Param("userId") Long userId);

    int getTotalNums(@Param("teamId") Long teamId, @Param("userId") Long userId);

    int bindTeam(@Param("item") TeamInfoDto teamInfoDto);

    int teamRelationCount(@Param("teamId")Long teamId, @Param("mId")Long mId, @Param("mType") int mType);
}