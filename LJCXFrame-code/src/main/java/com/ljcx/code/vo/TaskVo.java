package com.ljcx.code.vo;

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
     * 文件列表
     */
    private List<SysFileBean> fileList;


    /**
     * 飞行总次数
     */
    private int flightCount;

    /**
     * 飞行总时长
     */
    private double flightHours;

    /**
     * 飞行总距离
     */
    private double flightLength;

}
