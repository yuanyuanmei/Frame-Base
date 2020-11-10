package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.platform.beans.TeamInfoBean;
import com.ljcx.platform.dto.TeamInfoDto;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TeamInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


public interface TeamInfoDao extends BaseMapper<TeamInfoBean> {


    List<TeamInfoVo> list(@Param("item") TeamInfoDto teamInfoDto);

    List<TeamInfoVo> teamList(@Param("item") TeamInfoDto teamInfoDto);

    TeamInfoVo info(@Param("id") Long id);

    List<MapVo> getDataNums(@Param("teamId") Long teamId, @Param("userId") Long userId);

    int getTotalNums(@Param("teamId") Long teamId, @Param("userId") Long userId);


}
