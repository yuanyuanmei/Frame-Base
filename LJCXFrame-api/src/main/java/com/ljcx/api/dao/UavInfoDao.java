package com.ljcx.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.api.dto.UavInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 无人机信息表
 *
 * @author dm
 * @date 2019-11-14 16:52:21
 */

public interface UavInfoDao extends BaseMapper<UavInfoBean> {

    IPage<UavInfoBean> pageList(IPage<UavInfoBean> page, @Param("item") UavInfoDto uavInfoDto);

    List<UavInfoBean> listByTeamId(Long TeamId);

}
