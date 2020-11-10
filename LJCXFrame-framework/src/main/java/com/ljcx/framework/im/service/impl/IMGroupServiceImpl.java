package com.ljcx.framework.im.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.framework.im.common.IMUtil;
import com.ljcx.framework.im.dto.IMGroupDto;
import com.ljcx.framework.im.dto.IMGroupGetMessageDto;
import com.ljcx.framework.im.dto.IMGroupMemberDto;
import com.ljcx.framework.im.dto.IMGroupMessageDto;
import com.ljcx.framework.im.enums.RESTEnum;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMGroupService;
import org.springframework.stereotype.Service;

@Service
public class IMGroupServiceImpl implements IMGroupService {

    @Override
    public IMResponse groupCreate(IMGroupDto groupDto) {
        String s = IMUtil.sendUrl(RESTEnum.GROUP_CREATE, JSONObject.toJSONString(groupDto));
        return JSONObject.parseObject(s, IMResponse.class);
    }

    @Override
    public IMResponse addMember(IMGroupMemberDto groupMemberDto) {
        String s = IMUtil.sendUrl(RESTEnum.GROUP_ADD_MEMBER, JSONObject.toJSONString(groupMemberDto));
        return JSONObject.parseObject(s, IMResponse.class);
    }

    @Override
    public IMResponse sendMsg(IMGroupMessageDto groupMessageDto) {
        String s = IMUtil.sendUrl(RESTEnum.GROUP_SEND_MSG, JSONObject.toJSONString(groupMessageDto));
        return JSONObject.parseObject(s, IMResponse.class);
    }

    @Override
    public IMResponse getMsg(IMGroupGetMessageDto groupGetMessageDto) {
        String s = IMUtil.sendUrl(RESTEnum.GROUP_GET_MSG, JSONObject.toJSONString(groupGetMessageDto));
        return JSONObject.parseObject(s, IMResponse.class);
    }

    @Override
    public IMResponse delMember(IMGroupMemberDto groupMemberDto) {
        String s = IMUtil.sendUrl(RESTEnum.GROUP_DEL_MEMBER, JSONObject.toJSONString(groupMemberDto));
        return JSONObject.parseObject(s, IMResponse.class);
    }
}
