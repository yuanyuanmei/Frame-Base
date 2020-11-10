package com.ljcx.framework.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.framework.im.enums.GroupTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "群组信息不能为空")
public class IMGroupGetMessageDto implements Serializable
{

    private static final long serialVersionUID = 4043470238789599973L;

    @NotNull(message = "群Id不能为空")
    @JSONField(name = "GroupId")
    private String GroupId;

    @Min(value = 1)
    @Max(value = 20)
    @JSONField(name = "ReqMsgNumber")
    private Integer ReqMsgNumber;

    @JSONField(name = "ReqMsgSeq")
    private Integer ReqMsgSeq;

}
