package com.ljcx.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ljcx.api.enums.UavStateEnums;
import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "无人机信息不能为空")
public class UavInfoDto extends PageDto {

    /**
     * id
     */
    private Long id;
    /**
     * 团队Id
     */
    private Long teamId;
    /**
     * 序列号
     */
    private String no;
    /**
     * 型号
     */
    private String model;
    /**
     * 状态
     * 1.下线，2.上线，3.异常
     */
    private Integer status;
    /**
     * 名称
     */
    private String name;

    /**
     * 速度
     */
    private Double speed;
    /**
     * 高度
     */
    private Double high;
    /**
     * 航向
     */
    private String course;
    /**
     * 电池
     */
    private Integer electricity;
    /**
     * 网络
     */
    private String network;

    /**
     * 飞行状态
     */
    private Integer inFlight;

    /**
     * 飞行状态值
     */
    private String flightName;

    /**
     * 位置
     */
    private String position;

    /**
     * 坐标系
     */
    private String wcs;

    /**
     * 航向角
     */
    private double yaw;

    /**
     * id集合
     */
    private List<Long> ids;

    private Long createUser;

    public String getFlightName() {
        return UavStateEnums.codeOf(inFlight);
    }
}
