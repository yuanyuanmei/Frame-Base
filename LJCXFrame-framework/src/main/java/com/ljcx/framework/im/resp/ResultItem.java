package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ResultItem {

    /**
     * 请求处理的用户Id
     */
    @JSONField(name = "UserId")
    private String UserId;

    /**
     * 结果码，0表示成功，非0表示失败
     */
    @JSONField(name = "ResultCode")
    private Integer ResultCode;

    /**
     * 结果信息
     */
    @JSONField(name = "ResultInfo")
    private String ResultInfo;

    /**
     * 账号状态
     */
    @JSONField(name = "AccountStatus")
    private String AccountStatus;

}
