package com.ljcx.framework.sys.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "字典信息不能为空")
public class SysDicDto extends PageDto {

    @NotBlank(message = "方法名称不能为空")
    private String op;

    private Integer seq;
    //ID
    private Integer id;
    //编码
    @NotBlank(message = "编码不能为空")
    private String code;
    //名称
    @NotBlank(message = "名称不能为空")
    private String name;
    //备注
    private String memo;
    //排序
    private Integer sort;
    //创建用户
    private Long createUser;
}
