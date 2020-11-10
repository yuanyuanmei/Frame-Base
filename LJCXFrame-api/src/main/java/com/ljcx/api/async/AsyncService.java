package com.ljcx.api.async;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dto.PanoramaDto;

/**
 * 异步方法
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface AsyncService {

    /**
     * 创建全景图
     */
    void executeGenVtour();
}

