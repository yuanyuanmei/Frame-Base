package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.beans.CarInfoBean;
import com.ljcx.api.dto.CarInfoDto;
import com.ljcx.api.service.CarInfoService;
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
 * 车controller
 */
@Api(value = "车模块")
@RestController
@RequestMapping("/team/car")
@Slf4j
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private IGenerator generator;

    /**
     * 分页查询列表
     * @param info
     * info(carInfoDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    //@RequiresPermissions("team:equipment:query")
    public ResponseInfo pageList(@RequestBody String info) {
        CarInfoDto carInfoDto = JSONObject.parseObject(info, CarInfoDto.class);
        return new ResponseInfo(carInfoService.pageList(carInfoDto));
    }

    @PostMapping("/save")
    //@RequiresPermissions("team:equipment:add")
    public ResponseInfo save(@RequestBody String info){
        CarInfoDto carInfoDto = JSONObject.parseObject(info, CarInfoDto.class);
        return carInfoService.saveCar(carInfoDto);
    }

    @PostMapping("/update")
    //@RequiresPermissions("team:equipment:update")
    public ResponseInfo update(@RequestBody String info){
        CarInfoDto carInfoDto = JSONObject.parseObject(info, CarInfoDto.class);
        CarInfoBean bean = generator.convert(carInfoDto, CarInfoBean.class);
        if(carInfoDto.getId() == null){
            return new ResponseInfo().failed("id不能为空");
        }
        carInfoService.updateById(bean);
        return new ResponseInfo().success("更新成功");
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    //@RequiresPermissions("team:equipment:del")
    public ResponseInfo del(@RequestBody String info){
        CarInfoDto carInfoDto = JSONObject.parseObject(info, CarInfoDto.class);
        carInfoService.removeByIds(carInfoDto.getIds());
        return new ResponseInfo().success("删除成功");
    }



}
