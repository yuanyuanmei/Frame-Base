package com.ljcx.code.controller.bussiness;

import com.ljcx.code.beans.SceneReportBean;
import com.ljcx.code.dto.SceneReportDto;
import com.ljcx.code.enums.ScenereportTypeEnums;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.code.service.SceneReportService;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 现场上报
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@RestController
@RequestMapping("/data/scenereport")
public class SceneReportController {

    @Autowired
    private SceneReportService sceneReportService;

    @Autowired
    private IGenerator generator;

    /**
     * 分页查询列表
     * @param info
     * info(sceneReportDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("data:scenereport:query")
    public ResponseInfo pageList(@RequestBody String info) {
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        return new ResponseInfo(sceneReportService.pageList(sceneReportDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("data:scenereport:add")
    public ResponseInfo save(@RequestBody String info){
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        SceneReportBean bean = generator.convert(sceneReportDto, SceneReportBean.class);
        sceneReportService.saveOrUpdate(bean);
        return new ResponseInfo().success("保存成功");
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    @RequiresPermissions("data:scenereport:del")
    public ResponseInfo del(@RequestBody String info){
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        sceneReportService.removeByIds(sceneReportDto.getIds());
        return new ResponseInfo().success("删除成功");
    }

    /**
     * 获得枚举列表
     * @return
     */
    @PostMapping("/enumsList")
    @RequiresPermissions("data:scenereport:query")
    public ResponseInfo enumsList(){
        return new ResponseInfo(ScenereportTypeEnums.mapList());
    }

}
