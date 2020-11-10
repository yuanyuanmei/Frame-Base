
package com.ljcx.framework.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.beans.SysDicBean;
import com.ljcx.framework.sys.beans.SysDicDescBean;
import com.ljcx.framework.sys.dto.SysDicDto;

import java.util.List;


/**
 * 系统日志
 *
 */
public interface SysDicService extends IService<SysDicBean> {


    IPage<SysDicDescBean> pageList(SysDicDto sysDicDto);

    List<SysDicDescBean> list(SysDicDto sysDicDto);

    ResponseInfo save(SysDicDto sysDicDto);

    ResponseInfo del(SysDicDto sysDicDto);
}
