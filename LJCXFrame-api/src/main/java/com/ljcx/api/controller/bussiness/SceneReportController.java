package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.dto.SceneReportDto;
import com.ljcx.api.enums.ScenereportTypeEnums;
import com.ljcx.api.service.SceneReportService;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.constants.UserConstants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    //@RequiresPermissions("data:scenereport:query")
    @ValidateCustom(SceneReportDto.class)
    public ResponseInfo pageList(@RequestBody String info, HttpServletRequest request) {
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        //sceneReportDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return new ResponseInfo(sceneReportService.pageList(sceneReportDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("data:scenereport:add")
    @ValidateCustom(SceneReportDto.class)
    public ResponseInfo save(@RequestBody String info,HttpServletRequest request){
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        sceneReportDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return sceneReportService.save(sceneReportDto);
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    //@RequiresPermissions("data:scenereport:del")
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
    //@RequiresPermissions("data:scenereport:query")
    public ResponseInfo enumsList(){
        return new ResponseInfo(ScenereportTypeEnums.mapList());
    }

}
