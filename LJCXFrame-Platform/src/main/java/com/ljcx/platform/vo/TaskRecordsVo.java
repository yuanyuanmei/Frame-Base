package com.ljcx.platform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.platform.beans.RoutesPlanBean;
import com.ljcx.platform.beans.TaskRecordsBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
