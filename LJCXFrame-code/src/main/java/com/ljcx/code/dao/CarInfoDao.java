package com.ljcx.code.dao;

import com.ljcx.code.beans.CarInfoBean;
import com.ljcx.code.dto.CarInfoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.code.vo.CarInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备车信息表
 *
 * @author dm
 * @date 2019-11-14 16:52:21
 */

public interface CarInfoDao extends BaseMapper<CarInfoBean> {

    IPage<CarInfoBean> pageList(IPage<CarInfoBean> page, @Param("item") CarInfoDto carInfoDto);

    List<CarInfoVo> equipCarList();
}
