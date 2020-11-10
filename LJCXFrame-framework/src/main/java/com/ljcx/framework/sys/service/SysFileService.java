package com.ljcx.framework.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dto.SysFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 附件列表
 *
 * @author dm
 * @date 2019-11-19 14:42:16
 */
public interface SysFileService extends IService<SysFileBean> {

    IPage<SysFileBean> pageList(SysFileDto sysFileDto);

    SysFileBean upload(MultipartFile file) throws Exception;

    ResponseInfo del(SysFileDto fileDto);
}

