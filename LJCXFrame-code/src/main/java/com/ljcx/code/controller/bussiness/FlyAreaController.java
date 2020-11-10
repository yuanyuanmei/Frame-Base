package com.ljcx.code.controller.bussiness;

import com.ljcx.code.beans.FlyAreaBean;
import com.ljcx.code.dto.FlyAreaDto;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.code.service.FlyAreaService;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 飞行区域
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@RestController
@RequestMapping("/data/flyarea")
public class FlyAreaController {
    @Autowired
    private FlyAreaService flyAreaService;

    @Autowired
    private IGenerator generator;

    /**
     * 分页查询列表
     * @param info
     * info(flyAreaDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("data:flyarea:query")
    public ResponseInfo pageList(@RequestBody String info) {
        FlyAreaDto flyAreaDto = JSONObject.parseObject(info, FlyAreaDto.class);
        return new ResponseInfo(flyAreaService.pageList(flyAreaDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("data:flyarea:add")
    public ResponseInfo save(@RequestBody String info){
        FlyAreaDto flyAreaDto = JSONObject.parseObject(info, FlyAreaDto.class);
        FlyAreaBean bean = generator.convert(flyAreaDto, FlyAreaBean.class);
        flyAreaService.saveOrUpdate(bean);
        return new ResponseInfo().success("保存成功");
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    @RequiresPermissions("data:flyarea:del")
    public ResponseInfo del(@RequestBody String info){
        FlyAreaDto flyAreaDto = JSONObject.parseObject(info, FlyAreaDto.class);
        flyAreaService.removeByIds(flyAreaDto.getIds());
        return new ResponseInfo().success("删除成功");
    }


}
