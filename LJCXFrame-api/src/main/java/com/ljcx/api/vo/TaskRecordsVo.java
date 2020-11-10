package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskRecordsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //型号
    private String model;

    //编号
    private String no;

    //人员
    private String nickname;

    //完成时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //飞行时长
    private Long flightSeconds;

}
