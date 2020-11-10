package com.ljcx.framework.im.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "账号信息不能为空")
public class IMAccountDto
{


    @NotBlank(message = "操作类型不能为空")
    private String opt;

    /**
     * 用户Id集合
     */
    private String[] usernames;
}
