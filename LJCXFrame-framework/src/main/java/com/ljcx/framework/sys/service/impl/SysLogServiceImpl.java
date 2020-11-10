
package com.ljcx.framework.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.beans.SysLogBean;
import com.ljcx.framework.sys.dao.SysLogDao;
import com.ljcx.framework.sys.service.SysLogService;
import org.springframework.stereotype.Service;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogBean> implements SysLogService {

}
