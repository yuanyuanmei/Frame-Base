package com.ljcx.framework.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.framework.im.resp.IMMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "群消息信息不能为空")
public class IMGroupMessageDto
{

    /**
     *  群Id 必填
     */
    @NotNull(message = "群Id不能为空")
    @JSONField(name = "GroupId")
    private String GroupId;

    /**
     *  随机数 必填
     */
    @JSONField(name = "Random")
    private Integer Random = NumberUtils.toInt(UUID.randomUUID().toString().replace("-",""));

    /**
     * 	消息的优先级
     */
    @JSONField(name = "MsgPriority")
    private String MsgPriority;

    /**
     * 消息内容 必填
     */
    @JSONField(name = "MsgBody")
    private List<IMMessage> MsgBody;

    /**
     * 消息来源帐号
     */
    @JSONField(name = "From_Account")
    private String From_Account;

    /**
     * 离线推送信息配置
     */
    @JSONField(name = "OfflinePushInfo")
    private Object OfflinePushInfo;

    /**
     * 消息回调禁止开关
     */
    @JSONField(name = "ForbidCallbackControl")
    private List<Object> ForbidCallbackControl;

    /**
     * 1表示消息仅发送在线成员 0默认所有
     */
    @JSONField(name = "OnlineOnlyFlag")
    private Integer OnlineOnlyFlag;




}
