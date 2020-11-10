package com.ljcx.framework.im.service;

import com.ljcx.framework.im.dto.IMGroupDto;
import com.ljcx.framework.im.dto.IMGroupGetMessageDto;
import com.ljcx.framework.im.dto.IMGroupMemberDto;
import com.ljcx.framework.im.dto.IMGroupMessageDto;
import com.ljcx.framework.im.resp.IMResponse;

public interface IMGroupService {

    IMResponse groupCreate(IMGroupDto groupDto);

    IMResponse addMember(IMGroupMemberDto groupMemberDto);

    IMResponse sendMsg(IMGroupMessageDto groupMessageDto);

    IMResponse getMsg(IMGroupGetMessageDto groupGetMessageDto);

    IMResponse delMember(IMGroupMemberDto groupMemberDto);
}
