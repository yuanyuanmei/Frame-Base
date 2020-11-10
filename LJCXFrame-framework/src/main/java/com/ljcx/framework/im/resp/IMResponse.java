package com.ljcx.framework.im.resp;

import lombok.Data;

import java.util.List;

@Data
public class IMResponse {

    /**
     * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
     */
    private String ActionStatus;

    /**
     * 错误码，0表示成功，非0表示失败
     */
    private Integer ErrorCode;

    /**
     * 错误信息
     */
    private String ErrorInfo;

    /**
     * 群Id
     */
    private String GroupId;


    /**
     * 结果对象
     */
    private ResultItem ResultItem;

    /**
     * 成员列表(加入成员接口)
     */
    private List<IMMember> MemberList;

    /**
     * 消息发送的时间戳 (发送消息接口)
     */
    private Integer MsgTime;

    /**
     * 消息标识Id (发送消息接口)
     */
    private Integer MsgSeq;

    /**
     * 是否返回了请求区间的全部消息
     * 当消息长度太长或者区间太大（超过20）导致无法返回全部消息时，值为0
     * 当消息长度太长或者区间太大（超过20）且所有消息都过期时，值为2
     */
    private Integer IsFinished;

    /**
     * 返回的消息列表
     */
    private List<IMRspMsg> RspMsgList;






}
