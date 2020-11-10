package com.ljcx.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "通话状态不能为空")
public class CallStateDto implements Serializable {

    private static final long serialVersionUID = 4043470238789599973L;
    /**
     * 团队Id
     */
    @NotNull(message = "团队Id不能为空")
    private Long teamId;

    /**
     * 发起通话用户
     */
    @NotNull(message = "发送Id不能为空")
    private Long callingUser;
    /**
     * 接收Id
     */
    @NotNull(message = "接收Id不能为空")
    @JSONField(serialize =false)
    private Long calledUser;

    private String calledUserName;

    /**
     * 通话状态 0.正常 1.拒绝
     */
    private int state = 0;
}
