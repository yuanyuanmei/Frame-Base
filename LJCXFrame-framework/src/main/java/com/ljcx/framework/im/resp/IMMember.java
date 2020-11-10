package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 成员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IMMember {

    /**
     * 用户名
     */
    @JSONField(name = "Member_Account")
    private String Member_Account;

    /**
     * 结果 0-失败；1-成功；2-已经是群成员；3-等待被邀请者确认
     */
    @JSONField(name = "Result")
    private Integer Result;

    public IMMember(String member_Account) {
        Member_Account = member_Account;
    }
}
