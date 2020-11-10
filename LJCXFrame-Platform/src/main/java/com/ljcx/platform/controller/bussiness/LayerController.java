package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.platform.beans.PanoramaBean;
import com.ljcx.platform.dto.FlyAreaDto;
import com.ljcx.platform.service.FlyAreaService;
import com.ljcx.platform.service.LayerService;
import com.ljcx.platform.service.PanoramaService;
import com.ljcx.platform.vo.LayerBeanVo;
import com.ljcx.platform.vo.LayerVo;
import com.ljcx.platform.vo.PanoramaVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 图层控制层
 */
@RestController
@RequestMapping("/layer")
@Slf4j
public class LayerController {

    @Autowired
    private PanoramaService panoramaService;

    @Autowired
    private LayerService layerService;

    @Autowired
    private FlyAreaService flyAreaService;

    /**
     * 图层列表
     * @return
     */
    @PostMapping("/all/list")
    //@RequiresPermissions("data:panorama:query")
    public ResponseInfo list(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject == null || jsonObject.getLong("teamId") == null){
            return new ResponseInfo().failed("团队Id不能为空");
        }
        List<PanoramaVo> panoramaList = panoramaService.listByTeamId(jsonObject.getLong("teamId"));
        List<LayerBeanVo> layerList = layerService.listByTeamId(jsonObject.getLong("teamId"));
        LayerVo layerVo = new LayerVo();
        layerVo.setPanoramaList(panoramaList);
        layerVo.setLayerList(layerList);
        layerVo.setWarnFlyArea(flyAreaService.getListByCategory(new FlyAreaDto(0)));
        layerVo.setAuthFlyArea(flyAreaService.getListByCategory(new FlyAreaDto(1)));
        layerVo.setNoFlyArea(flyAreaService.getListByCategory(new FlyAreaDto(2)));
        layerVo.setLimitFlyArea(flyAreaService.getListByCategory(new FlyAreaDto(2,1.0)));
        layerVo.setEnhancedWarnFlyArea(flyAreaService.getListByCategory(new FlyAreaDto(3)));
        return new ResponseInfo(layerVo);
    }


    /**
     * 根据category类型获取图层信息
     * @return
     */
    @PostMapping("/flyArea/listByCategory")
    //@RequiresPermissions("data:flyarea:query")
    @ValidateCustom(FlyAreaDto.class)
    public ResponseInfo flyAreaListByCategory(@RequestBody String info) {
        FlyAreaDto flyAreaDto = JSONObject.parseObject(info, FlyAreaDto.class);
        return new ResponseInfo(flyAreaService.getListByCategory(flyAreaDto));
    }



}
