package com.ljcx.framework.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.framework.im.enums.GroupTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "群组信息不能为空")
public class IMGroupDto implements Serializable
{

    private static final long serialVersionUID = 4043470238789599973L;

    @NotNull(message = "团队Id不能为空")
    @JSONField(serialize = false)
    private Long teamId;

    @JSONField(name = "GroupId")
    private String GroupId;

    /**
     * 群主用户名
     */
    @JSONField(name = "Owner_Account")
    private String Owner_Account;

    /**
     * 群类型
     */
    @JSONField(name = "Type")
    private String Type = GroupTypeEnum.Public.value();

    /**
     * 群聊名称
     */
    @JSONField(name = "Name")
    private String Name;

    /**
     * 群简介
     */
    @JSONField(name = "Introduction")
    private String Introduction;

    /**
     * 群公告
     */
    @JSONField(name = "Notification")
    private String Notification;

    /**
     * 群头像
     */
    @JSONField(name = "FaceUrl")
    private String FaceUrl;

    /**
     * 最大群成员数量
     */
    @JSONField(name = "MaxMemberCount")
    private String MaxMemberCount;

    /**
     * 申请加群处理方式
     */
    @JSONField(name = "ApplyJoinOption")
    private String ApplyJoinOption;

}
