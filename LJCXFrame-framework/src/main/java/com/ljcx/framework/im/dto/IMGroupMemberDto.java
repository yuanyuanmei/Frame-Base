package com.ljcx.framework.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.framework.im.resp.IMMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "群成员信息不能为空")
public class IMGroupMemberDto
{
    /**
     * 群Id
     */
    @NotBlank(message = "群Id不能为空")
    @JSONField(name = "GroupId")
    private String GroupId;

    /**
     * 是否静默加入 0 否，1.是
     */
    @JSONField(name = "Silence")
    private Integer Silence;

    /**
     * 成员集合
     */
    @JSONField(name = "MemberList")
    private List<IMMember> MemberList;

    /**
     * 理由
     */
    @JSONField(name = "Reason")
    private String Reason;

    /**
     * 踢出成员账号列表
     */
    @JSONField(name = "MemberToDel_Account")
    private List<String> MemberToDel_Account;

}
