package com.ljcx.platform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.platform.beans.LayerBean;
import com.ljcx.platform.beans.PanoramaBean;
import com.ljcx.user.vo.MemberVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class LayerVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全景图层
     */
    private List<PanoramaVo> panoramaList;

    /**
     * 图层列表管理
     */
    private List<LayerBeanVo> layerList;

    /**
     * 禁飞区
     */
    private List<FlyAreaVo> noFlyArea;

    /**
     * 限高区
     */
    private List<FlyAreaVo> limitFlyArea;

    /**
     * 授权区 AUTHORIZATION
     */
    private List<FlyAreaVo> authFlyArea;

    /**
     * 警示区
     */
    private List<FlyAreaVo> warnFlyArea;

    /**
     * 加强警示区ENHANCED_WARNING
     */
    private List<FlyAreaVo> enhancedWarnFlyArea;

}
