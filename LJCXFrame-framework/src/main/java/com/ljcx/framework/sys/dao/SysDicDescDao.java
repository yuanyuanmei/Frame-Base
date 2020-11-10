
package com.ljcx.framework.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.framework.sys.beans.SysDicBean;
import com.ljcx.framework.sys.beans.SysDicDescBean;
import com.ljcx.framework.sys.dto.SysDicDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志
 *
 */
public interface SysDicDescDao extends BaseMapper<SysDicDescBean> {

    IPage<SysDicDescBean> pageList(IPage<SysDicBean> page, @Param("item") SysDicDto sysDicDto);

    List<SysDicDescBean> pageList(@Param("item") SysDicDto sysDicDto);

    int updateByCode(@Param("oldCode") String oldCode,@Param("newCode") String newCode);
}
