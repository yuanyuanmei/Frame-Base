package com.ljcx.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.platform.beans.PanoramaBean;
import com.ljcx.platform.dto.PanoramaDto;
import com.ljcx.platform.vo.PanoramaVo;

import java.util.List;
import java.util.Set;

/**
 * 全景图
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
public interface PanoramaService extends IService<PanoramaBean> {

    List<PanoramaVo> listByTeamId(Long teamId);
}

