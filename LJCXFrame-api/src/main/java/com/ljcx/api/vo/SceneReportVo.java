package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.api.enums.ScenereportTypeEnums;
import com.ljcx.framework.sys.beans.SysFileBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SceneReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 服务类型
     */
    private Integer type;

    /**
     * 服务类型名称
     */
    private String typeName;

    /**
     * 内容
     */
    private String content;

    /**
     * 横坐标
     */
    private String lng;

    /**
     * 纵坐标
     */
    private String lat;

    /**
     * 地址
     */
    private String address;

    private String nickname;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<SysFileBean> files;


}
