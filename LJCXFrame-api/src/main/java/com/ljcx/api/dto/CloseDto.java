package com.ljcx.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "关闭对象不能为空")
public class CloseDto {

    @NotNull(message = "设备id不能为空")
    private Long id;

    @NotNull(message = "团队id不能为空")
    private Long teamId;

    @NotNull(message = "类型不能为空")
    //1.无人机，2.指挥车，3.用户,4.直播
    private Integer type;

    private Integer status = 0;
    

}
