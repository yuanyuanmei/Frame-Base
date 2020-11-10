package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.api.beans.RoutesPlanBean;
import com.ljcx.api.beans.TaskLogBean;
import com.ljcx.api.beans.TaskRecordsBean;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.framework.sys.beans.SysFileBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TaskVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 名称
     */
    private String name;

    /**
     * 任务类型
     */
    private Integer type;

    /**
     * 任务类型名称
     */
    private String typeName;

    /**
     * 任务内容
     */
    private String memo;

    /**
     * 完成状态
     */
    private Integer completeStatus;

    /**
     * 完成状态名称
     */
    private String completeStatusName;

    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 创建人名称
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 航线列表
     */
    private List<RoutesPlanBean> routesList;

    /**
     * 文件列表
     */
    private List<SysFileBean> fileList;

    /**
     * 记录列表
     */
    private List<TaskRecordsBean> recordsList;

    /**
     * 日志列表
     */
    private List<TaskLogVo> logList;

    /**
     * 飞行总次数
     */
    private int flightCount;

    /**
     * 飞行总距离
     */
    private double flightLength;

    /**
     * 飞行总时长(单位秒钟)
     */
    private Long flightSumSeconds;

    /**
     * 飞行平均时长（单位秒钟）
     */
    private Long flightAvgSeconds;

    private String flightSumSecondsStr;

    private String flightAvgSecondsStr;

    public String getFlightSumSecondsStr() {
        if(flightSumSeconds != null){
            return DateUtils.secondToTime(flightSumSeconds);
        }
        return "";
    }

    public String getFlightAvgSecondsStr() {
        if(flightAvgSeconds != null){
            return DateUtils.secondToTime(flightAvgSeconds);
        }
        return "";
    }

    public String getCompleteStatusName() {
        if(completeStatus == 1){
            return "已完成";
        }
        return "未完成";
    }

}
