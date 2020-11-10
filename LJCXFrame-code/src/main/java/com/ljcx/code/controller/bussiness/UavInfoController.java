package com.ljcx.code.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.code.beans.UavInfoBean;
import com.ljcx.code.dto.UavInfoDto;
import com.ljcx.code.service.TeamInfoService;
import com.ljcx.code.service.UavInfoService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 无人机controller
 */
@Api(value = "无人机模块")
@RestController
@RequestMapping("/team/uav")
@Slf4j
public class UavInfoController {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UavInfoService uavInfoService;

    @Autowired
    private TeamInfoService teamInfoService;

    /**
     * 分页查询列表
     * @param info
     * info(uavInfoDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("team:equipment:query")
    public ResponseInfo pageList(@RequestBody String info) {
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        return new ResponseInfo(uavInfoService.pageList(uavInfoDto));
    }

    @PostMapping("/save")
    @RequiresPermissions("team:equipment:add")
    public ResponseInfo save(@RequestBody String info){
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        UavInfoBean bean = generator.convert(uavInfoDto, UavInfoBean.class);
        uavInfoService.saveOrUpdate(bean);
        return new ResponseInfo().success("保存成功");
    }

    @PostMapping("/del")
    @RequiresPermissions("team:equipment:del")
    public ResponseInfo del(@RequestBody String info){
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        //删除团队关系表
        uavInfoDto.getIds().stream().forEach(item->{
            teamInfoService.delRelationShipByMid(item,1);
        });
        uavInfoService.removeByIds(uavInfoDto.getIds());
        return new ResponseInfo().success("删除成功");
    }

}
