package com.ljcx.api.controller.bussiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.api.dto.UavInfoDto;
import com.ljcx.api.service.UavInfoService;
import com.ljcx.api.shiro.jwt.JwtUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.constants.UserConstants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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
    private ActivemqProducer producer;
    /**
     * 分页查询列表
     * @param info
     * info(uavInfoDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    //@RequiresPermissions("team:equipment:query")
    public ResponseInfo pageList(@RequestBody String info) {
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        return new ResponseInfo(uavInfoService.pageList(uavInfoDto));
    }

    @PostMapping("/save")
    //@RequiresPermissions("team:equipment:add")
    public ResponseInfo save(@RequestBody String info, HttpServletRequest request){
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        uavInfoDto.setCreateUser(JwtUtils.getUserId(request.getHeader(UserConstants.TOKEN)));
        return uavInfoService.saveUav(uavInfoDto);
    }

    @PostMapping("/update")
    //@RequiresPermissions("team:equipment:update")
    public ResponseInfo update(@RequestBody String info){
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        UavInfoBean bean = generator.convert(uavInfoDto, UavInfoBean.class);
        if(uavInfoDto.getId() == null){
            return new ResponseInfo().failed("id不能为空");
        }
        uavInfoService.updateById(bean);
        return new ResponseInfo().success("更新成功");
    }

    @PostMapping("/del")
    //@RequiresPermissions("team:equipment:del")
    public ResponseInfo del(@RequestBody String info){
        UavInfoDto uavInfoDto = JSONObject.parseObject(info, UavInfoDto.class);
        uavInfoService.removeByIds(uavInfoDto.getIds());
        return new ResponseInfo().success("删除成功");
    }

    @PostMapping("/testUavState")
    public ResponseInfo testUavState(@RequestBody String info){
        UavInfoDto uavInfoDto = new UavInfoDto();
        uavInfoDto.setId(9l);
        uavInfoDto.setPosition("113.928705,22.572364");
        uavInfoDto.setStatus(1);
        uavInfoDto.setInFlight(1);
        uavInfoDto.setTeamId(9l);
        producer.sendTopic("topic_uav_state", JSON.toJSONString(uavInfoDto));
        return new ResponseInfo().success("发送成功");
    }

    @PostMapping("/testMemberState")
    public ResponseInfo testMemberState(@RequestBody String info){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",4);
        jsonObject.put("teamId",9);
        jsonObject.put("status",1);
        jsonObject.put("type",3);
        jsonObject.put("name","孙总");
        jsonObject.put("position","13.952108,22.533672");
        producer.sendTopic("topic_member_state", jsonObject.toJSONString());
        return new ResponseInfo().success("发送成功");
    }

}
