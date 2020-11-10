package com.ljcx.code.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.code.enums.ScenereportTypeEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SceneReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

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

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;



}
