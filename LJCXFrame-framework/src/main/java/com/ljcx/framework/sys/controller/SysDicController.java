package com.ljcx.framework.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.beans.SysDicBean;
import com.ljcx.framework.sys.dao.SysDicDao;
import com.ljcx.framework.sys.dto.SysDicDto;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.framework.sys.service.SysDicService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典controller
 */
@Api(value = "字典模块")
@RestController
@RequestMapping(value = "/sys/dic")
@Slf4j
public class SysDicController {

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private IGenerator generator;

    @PostMapping("/pageList")
    @RequiresPermissions("sys:dic:query")
    public ResponseInfo pageList(@RequestBody String info) {
        SysDicDto sysDicDto = JSONObject.parseObject(info, SysDicDto.class);
        return new ResponseInfo(sysDicService.pageList(sysDicDto));
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:dic:add")
    @ValidateCustom(SysDicDto.class)
    public ResponseInfo save(@RequestBody String info) {
        SysDicDto sysDicDto = JSONObject.parseObject(info, SysDicDto.class);
        return sysDicService.save(sysDicDto);
    }

    @PostMapping("/list")
    @RequiresPermissions("sys:dic:query")
    public ResponseInfo list(@RequestBody String info) {
        SysDicDto sysDicDto = JSONObject.parseObject(info, SysDicDto.class);
        return new ResponseInfo(sysDicService.list(sysDicDto));
    }

    @PostMapping("/del")
    @RequiresPermissions("sys:dic:del")
    public ResponseInfo del(@RequestBody String info) {
        SysDicDto sysDicDto = JSONObject.parseObject(info,SysDicDto.class);
        return sysDicService.del(sysDicDto);
    }

}
