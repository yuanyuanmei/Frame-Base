package com.ljcx.code.dto;

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
     * 操作类型
     */
    private String type;
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
     * id集合
     */
    private List<Long> ids;
}
