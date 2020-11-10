package com.ljcx.platform.dto;

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
@NotNull(message = "通话信息不能为空")
public class CallDto implements Serializable {

    private static final long serialVersionUID = 4043470238789599973L;

    /**
     * 团队Id
     */
    @NotNull(message = "团队Id不能为空")
    private Long teamId;

    /**
     * 通话类型 1.单人通话 2.多人通话
     */
    @NotNull(message = "通话类型不能为空")
    private Integer type;

    /**
     * 发送状态 0.成功，1.失败
     */
    @NotNull(message = "发送状态不能为空")
    private Integer isFail;

    /**
     * 接收Id
     */
    @NotNull(message = "接收Id不能为空")
    @JSONField(serialize =false)
    private Long[] calledId;

    /**
     * 是否被邀请 0 未邀请 1.已邀请
     */
    @NotNull(message = "邀请状态不能为空")
    private Integer isInvite;

    /**
     * 发起通话用户
     */
    private Long callingUser;

    /**
     * 发起通话用户姓名
     */
    private String callingName;

    private String callingNickName;


    /**
     * 本人用户ID
     */
    private Long userId;

    /**
     * 本人用户名
     */
    private String username;

    /**
     * 本人用户签名
     */
    private String userSign;

    /**
     * 房间号
     */
    private Integer roomId;

    /**
     * 签名list
     */
    private List<RoomUserDto> roomUserList;

}
