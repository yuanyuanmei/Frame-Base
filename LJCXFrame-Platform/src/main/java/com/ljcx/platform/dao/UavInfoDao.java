package com.ljcx.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.platform.dto.StatusDto;
import com.ljcx.platform.dto.UavInfoDto;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.UavInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 无人机信息表
 *
 * @author dm
 * @date 2019-11-14 16:52:21
 */

public interface UavInfoDao extends BaseMapper<UavInfoBean> {

    IPage<UavInfoBean> pageList(IPage<UavInfoBean> page, @Param("item") UavInfoDto uavInfoDto);

    List<UavInfoVo> listByTeamId(Long teamId);

    List<UavInfoVo> listByUserId(Long userId);

    //List<MapVo> getStatus(@Param("teamId") Long teamId, @Param("userId") Long userId);

}
