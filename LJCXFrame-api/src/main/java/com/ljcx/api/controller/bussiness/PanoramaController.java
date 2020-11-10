package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.async.AsyncService;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dto.PanoramaDto;
import com.ljcx.api.service.PanoramaService;
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

    @Autowired
    private AsyncService asyncService;
    /**
     * 分页查询列表
     * @param info
     * info(panoramaDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    //@RequiresPermissions("data:panorama:add")
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
    //@RequiresPermissions("data:panorama:add")
    @ValidateCustom(PanoramaDto.class)
    public ResponseInfo save(@RequestBody String info, HttpServletRequest request){
        PanoramaDto panoramaDto = JSONObject.parseObject(info, PanoramaDto.class);
        panoramaDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        PanoramaBean bean = panoramaService.save(panoramaDto);
        asyncService.executeGenVtour();
        return new ResponseInfo(bean);
    }

    /**
     * 根据ID批量删除对象
     * @param info
     * @return
     */
    @PostMapping("/del")
    //@RequiresPermissions("data:panorama:del")
    public ResponseInfo del(@RequestBody String info){
        PanoramaDto panoramaDto = JSONObject.parseObject(info, PanoramaDto.class);
        panoramaService.removeByIds(panoramaDto.getIds());
        return new ResponseInfo().success("删除成功");
    }


}
