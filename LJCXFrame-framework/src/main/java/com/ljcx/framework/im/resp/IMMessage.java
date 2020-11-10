package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.framework.im.enums.IMMessageEnum;
import lombok.Data;


@Data
public class IMMessage {

    @JSONField(serialize = false)
    private Integer MsgId;
    /**
     * 消息类型
     */
    @JSONField(name = "MsgType")
    private String MsgType;

    /**
     * 消息内容
     */
    @JSONField(name = "MsgContent")
    private IMMsgContent MsgContent;

    public String getMsgType() {
        if(MsgId != null){
            return IMMessageEnum.codeOf(MsgId);
        }
        return MsgType;
    }
}
