package com.ljcx.code.controller.bussiness;

import com.ljcx.code.beans.PanoramaBean;
import com.ljcx.code.dto.PanoramaDto;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.framework.sys.service.IGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.code.service.PanoramaService;
import com.ljcx.common.utils.ResponseInfo;

/**
 * 全景图
 *
 * @author dm
 * @date 2019-11-18 16:51:15
 */
@RestController
@RequestMapping("/data/panorama")
public class PanoramaController {
    @Autowired
    private PanoramaService panoramaService;

    @Autowired
    private IGenerator generator;

    /**
     * 分页查询列表
     * @param info
     * info(panoramaDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("data:panorama:add")
    public ResponseInfo pageList(@RequestBody String info) {
        PanoramaDto panoramaDto = JSONObject.parseObject(info, PanoramaDto.class);
        return new ResponseInfo(panoramaService.pageList(panoramaDto));
    }

    /**
     * 保存对象信息
     * @param info
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("data:panorama:add")
    public ResponseInfo save(@RequestBody String info){
        PanoramaDto panoramaDto = JSONObject.parseObject(info, PanoramaDto.class);
        PanoramaBean bean = generator.convert(panoramaDto, PanoramaBean.class);
        bean.setCreateUser(UserUtil.getCurrentUser().getId());
        panoramaService.saveOrUpdate(bean);
        return new ResponseInfo().success("保存成功");
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    @RequiresPermissions("data:panorama:del")
    public ResponseInfo del(@RequestBody String info){
        PanoramaDto panoramaDto = JSONObject.parseObject(info, PanoramaDto.class);
        panoramaService.removeById(panoramaDto.getId());
        return new ResponseInfo().success("删除成功");
    }


}
