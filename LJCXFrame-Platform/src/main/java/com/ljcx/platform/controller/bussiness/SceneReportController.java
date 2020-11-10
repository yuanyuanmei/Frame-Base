package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.platform.dto.SceneReportDto;
import com.ljcx.platform.service.SceneReportService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询列表
     * @param info
     * info(sceneReportDto} 对象)
     * @return
     */
    @PostMapping("/list")
    //@RequiresPermissions("data:scenereport:query")
    @ValidateCustom(SceneReportDto.class)
    public ResponseInfo list(@RequestBody String info) {
        SceneReportDto sceneReportDto = JSONObject.parseObject(info, SceneReportDto.class);
        return new ResponseInfo(sceneReportService.listByTeamId(sceneReportDto));
    }

}
