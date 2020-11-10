package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.api.beans.RoutesPlanBean;
import com.ljcx.api.beans.TaskLogBean;
import com.ljcx.api.beans.TaskRecordsBean;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.framework.sys.beans.SysFileBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 团队ID
     */
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
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public TaskLogVo(Long taskId, String type, String name, String userName,Date createTime) {
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
