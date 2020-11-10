package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class IMRspMsg {

    /**
     * 消息发送者
     */
    @JSONField(name = "From_Account")
    private String From_Account;

    /**
     * 是否是空洞消息 0 不是，1.是
     */
    @JSONField(name = "IsPlaceMsg")
    private Integer IsPlaceMsg;

    /**
     * 消息内容
     */
    @JSONField(name = "MsgBody")
    private List<IMMessage> MsgBody;

    /**
     * 消息随机值
     */
    @JSONField(name = "MsgRandom")
    private String MsgRandom;

    /**
     * 用来标识唯一消息
     */
    @JSONField(name = "MsgSeq")
    private Integer MsgSeq;

    /**
     * 消息被发送的时间戳
     */
    @JSONField(name = "MsgTimeStamp")
    private Integer MsgTimeStamp;

}
