package com.ljcx.framework.sys.dao;

import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dto.SysFileDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.framework.sys.vo.SysFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 附件列表
 * 
 * @author dm
 * @date 2019-11-19 14:42:16
 */

public interface SysFileDao extends BaseMapper<SysFileBean> {

    IPage<SysFileBean> pageList(IPage<SysFileBean> page, @Param("item") SysFileDto sysFileDto);

    int deleteByIds(@Param("ids") List<Long> ids);

    int updateByMid(@Param("fileIds") List<Long> fileIds,@Param("mId") Long mId,@Param("mSrc") String mSrc);

    List<SysFileVo> listByReportId(@Param("reportId") Long reportId);

    List<SysFileVo> listByTaskId(@Param("taskId") Long taskId);

}
