package com.ljcx.code.dao;

import com.ljcx.code.beans.UavInfoBean;
import com.ljcx.code.dto.UavInfoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.vo.UavInfoVo;
import com.ljcx.user.beans.UserBaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 无人机信息表
 *
 * @author dm
 * @date 2019-11-14 16:52:21
 */

public interface UavInfoDao extends BaseMapper<UavInfoBean> {

    IPage<UavInfoBean> pageList(IPage<UavInfoBean> page, @Param("item") UavInfoDto uavInfoDto, @Param("currentUser") UserBaseBean userBaseBean);

    List<UavInfoVo> equipUavList();

}
