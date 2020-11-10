package com.ljcx.panor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.panor.beans.RefUserPanoBean;
import com.ljcx.panor.service.RefUserPanoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.ljcx.framework.sys.service.IGenerator;

import com.alibaba.fastjson.JSONObject;

import com.ljcx.panor.service.TPanoramaService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.panor.dto.TPanoramaDto;
import com.ljcx.panor.beans.TPanoramaBean;

/**
 * 全景图表
 *
 * @author dm
 * @date 2020-05-27 10:12:59
 */
@RestController
@RequestMapping("/panor/tpanorama")
public class TPanoramaController {
    @Autowired
    private TPanoramaService tPanoramaService;

    @Autowired
    private RefUserPanoService refUserPanoService;

    @Autowired
    private IGenerator generator;
    /**
     * 分页查询列表
     * @param info
     * info(tPanoramaDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    public ResponseInfo pageList(@RequestBody String info) {
        TPanoramaDto tPanoramaDto = JSONObject.parseObject(info, TPanoramaDto.class);
        return new ResponseInfo(tPanoramaService.pageList(tPanoramaDto));
    }

    @PostMapping("/addView")
    public ResponseInfo save(@RequestBody String info){
        TPanoramaDto tPanoramaDto = JSONObject.parseObject(info, TPanoramaDto.class);
        if(tPanoramaDto == null || tPanoramaDto.getPanoId() == null){
            return new ResponseInfo().failed("全景图ID不能为空");
        }
        TPanoramaBean bean = tPanoramaService.getById(tPanoramaDto.getPanoId());
        if(bean != null){
            bean.setViewsNumber(bean.getViewsNumber()+1);
            tPanoramaService.updateById(bean);
        }
        return new ResponseInfo().success("添加成功");
    }

    @PostMapping("/collect")
    @ValidateCustom(TPanoramaDto.class)
    public ResponseInfo collect(@RequestBody String info){
        TPanoramaDto tPanoramaDto = JSONObject.parseObject(info, TPanoramaDto.class);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",tPanoramaDto.getUserId());
        wrapper.eq("pano_id",tPanoramaDto.getPanoId());
        RefUserPanoBean searchBean = refUserPanoService.getOne(wrapper);
        if(searchBean == null){
            RefUserPanoBean refUserPanoBean = new RefUserPanoBean();
            refUserPanoBean.setPanoId(tPanoramaDto.getPanoId());
            refUserPanoBean.setUserId(tPanoramaDto.getUserId());
            refUserPanoService.save(refUserPanoBean);
            return new ResponseInfo().success("已收藏");
        }else{
            refUserPanoService.removeById(searchBean.getId());
            return new ResponseInfo().success("已取消收藏");
        }


    }

}
