package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.beans.FlyAreaBean;
import com.ljcx.api.dto.FlyAreaDto;
import com.ljcx.api.service.FlyAreaService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 飞行区域
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@RestController
@RequestMapping("/data/flyarea")
@Slf4j
public class FlyAreaController {
    @Autowired
    private FlyAreaService flyAreaService;

    @Autowired
    private IGenerator generator;


    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("data:flyarea:add")
    public ResponseInfo save(@RequestBody String info){
        List<FlyAreaDto> flyAreaDtos = JSONArray.parseArray(info, FlyAreaDto.class);
        return flyAreaService.save(flyAreaDtos);
    }




}
