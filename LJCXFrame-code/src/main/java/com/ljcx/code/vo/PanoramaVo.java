package com.ljcx.code.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PanoramaVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long teamId;

    private String teamName;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 上传人横坐标
     */
    private String lng;

    /**
     * 上传人纵坐标
     */
    private String lat;

    /**
     * 地址
     */
    private String address;

    private Integer shareStatus;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    

}
