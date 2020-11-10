package com.ljcx.code.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.code.enums.FlyCategoryEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class FlyAreaVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 类型
     */
    private Integer category;

    /**
     * 类型名称
     */
    private String categoryName;

    /**
     * 坐标
     */
    private String coordinate;

    /**
     * 飞行区域id（来自api）
     */
    private Long flyZoneId;

    /**
     * 飞行区域类型（来自api）
     */
    private String flyZoneType;

    /**
     * 名称
     */
    private String name;

    /**
     * 半径
     */
    private Double radius;

    /**
     * 原因
     */
    private String reason;

    /**
     * 形状
     */
    private String shape;

    /**
     * 最大飞行高度
     */
    private Double maximumFlightHeight;

    /**
     * 坐标顶点
     */
    private String vertices;

    /**
     * 上级id
     */
    private List<FlyAreaVo> child;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

}
