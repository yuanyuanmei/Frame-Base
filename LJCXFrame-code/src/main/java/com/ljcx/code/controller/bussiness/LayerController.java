package com.ljcx.code.controller.bussiness;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.ljcx.framework.sys.service.IGenerator;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.code.service.LayerService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.code.dto.LayerDto;
import com.ljcx.code.beans.LayerBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 所有图层
 *
 * @author dm
 * @date 2019-12-09 11:42:59
 */
@Slf4j
@RestController
@RequestMapping("/data/layer")
public class LayerController {
    @Autowired
    private LayerService layerService;

    @Autowired
    private IGenerator generator;


    /**
     * 分页查询列表
     * @param info
     * info(layerDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("data:layer:query")
    public ResponseInfo pageList(@RequestBody String info) {
        LayerDto layerDto = JSONObject.parseObject(info, LayerDto.class);
        return new ResponseInfo(layerService.pageList(layerDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("data:layer:add")
    public ResponseInfo save(@RequestBody String info) {
        LayerDto layerDto = JSONObject.parseObject(info, LayerDto.class);
        LayerBean bean = generator.convert(layerDto, LayerBean.class);
        layerService.saveOrUpdate(bean);
        return new ResponseInfo().success("保存成功");
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    @RequiresPermissions("data:layer:del")
    public ResponseInfo del(@RequestBody String info){
        LayerDto layerDto = JSONObject.parseObject(info, LayerDto.class);
        layerService.removeByIds(layerDto.getIds());
        return new ResponseInfo().success("删除成功");
    }


}
