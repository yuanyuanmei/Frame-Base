package com.ljcx.user.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "账号信息不能为空")
public class AccountDto extends PageDto {

    private Long id;

    private String account;
    
    private String password;

    private Boolean rememberMe = false;

    //手机号码
    private String phone;

    //昵称
    private String nickname;

    //email
    private String email;

    //teamId
    private Long teamId;

    //角色ID
    private List<Integer> roleIds;



}
