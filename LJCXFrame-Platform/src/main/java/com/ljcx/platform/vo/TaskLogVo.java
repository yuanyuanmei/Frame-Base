package com.ljcx.platform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long teamId;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 记录id
     */
    private Long recordsId;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 日志时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public TaskLogVo(Long teamId, Long taskId, String type, String name, String userName,Date createTime) {
        this.teamId = teamId;
        this.taskId = taskId;
        this.type = type;
        this.name = name;
        this.userName = userName;
        this.createTime = createTime;
    }

    public TaskLogVo(Long teamId,Long taskId,String taskName, String type, String name, String userName,Date createTime) {
        this.teamId = teamId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.type = type;
        this.name = name;
        this.userName = userName;
        this.createTime = createTime;
    }
}
