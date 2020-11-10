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
@NotNull(message = "车信息不能为空")
public class CarInfoDto extends PageDto {

    /**
     * id
     */
    private Long id;
    /**
     * 团队Id
     */
    private Long teamId;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 最后一次位置
     */
    private String lastLocation;

    /**
     * id集合
     */
    private List<Long> ids;
}
