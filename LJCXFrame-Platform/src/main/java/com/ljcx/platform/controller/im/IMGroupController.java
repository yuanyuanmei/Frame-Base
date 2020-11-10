package com.ljcx.platform.controller.im;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.im.dto.IMGroupDto;
import com.ljcx.framework.im.dto.IMGroupGetMessageDto;
import com.ljcx.framework.im.dto.IMGroupMemberDto;
import com.ljcx.framework.im.dto.IMGroupMessageDto;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMGroupService;
import com.ljcx.platform.beans.TeamInfoBean;
import com.ljcx.platform.service.TeamInfoService;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.service.UserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/im/group")
@Slf4j
public class IMGroupController {

    @Autowired
    private IMGroupService groupService;

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private UserBaseService userBaseService;

    @PostMapping("/create")
    @ValidateCustom(IMGroupDto.class)
    public ResponseInfo groupCreate(@RequestBody String info) {
        IMGroupDto imGroupDto = JSONObject.parseObject(info, IMGroupDto.class);
        TeamInfoBean teamBean = teamInfoService.getById(imGroupDto.getTeamId());
        if(teamBean == null){
            return new ResponseInfo().failed("团队数据为空");
        }
        if(StringUtils.isNotEmpty(teamBean.getImGroupId())){
            return new ResponseInfo(teamBean);
        }
        UserBaseBean userBaseBean = userBaseService.getById(teamBean.getUserId());
        imGroupDto.setGroupId("Group_"+teamBean.getId());
        imGroupDto.setName(teamBean.getName()+"的群聊");
        imGroupDto.setOwner_Account(userBaseBean.getUsername());
        IMResponse imResponse = groupService.groupCreate(imGroupDto);
        if(imResponse.getActionStatus().equals("OK")){
            teamBean.setImGroupId(imGroupDto.getGroupId());
            teamBean.setImGroupName(imGroupDto.getName());
            teamInfoService.updateById(teamBean);
            return new ResponseInfo(teamBean);
        }
        return new ResponseInfo(imResponse);
    }

    @PostMapping("/addMember")
    @ValidateCustom(IMGroupMemberDto.class)
    public ResponseInfo addMember(@RequestBody String info) {
        IMGroupMemberDto imGroupMemberDto = JSONObject.parseObject(info, IMGroupMemberDto.class);
        IMResponse imResponse = groupService.addMember(imGroupMemberDto);
        if(imResponse.getActionStatus().equals("OK")){
            return new ResponseInfo().success("添加成功");
        }
        return new ResponseInfo(imResponse);
    }

    @PostMapping("/sendMsg")
    @ValidateCustom(IMGroupMessageDto.class)
    public ResponseInfo sendMsg(@RequestBody String info) {
        IMGroupMessageDto imGroupMessageDto = JSONObject.parseObject(info, IMGroupMessageDto.class);
        IMResponse imResponse = groupService.sendMsg(imGroupMessageDto);
        if(imResponse.getActionStatus().equals("OK")){
            return new ResponseInfo().success("发送成功");
        }
        return new ResponseInfo(imResponse);
    }

    @PostMapping("/getMsg")
    @ValidateCustom(IMGroupGetMessageDto.class)
    public ResponseInfo getMsg(@RequestBody String info) {
        IMGroupGetMessageDto groupGetMessageDto = JSONObject.parseObject(info, IMGroupGetMessageDto.class);
        IMResponse imResponse = groupService.getMsg(groupGetMessageDto);
        return new ResponseInfo(imResponse);
    }
}
