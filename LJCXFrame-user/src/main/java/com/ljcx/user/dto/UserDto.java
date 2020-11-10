package com.ljcx.user.dto;

import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "用户信息不能为空")
public class UserDto extends PageDto {

    private Long id;

    private String username;

    private String nickname;

    private Integer status;

    //电话
    private String phone;

    //邮箱
    private String email;

    private Integer roleId;

    private Integer notRoleId;

    /**
     * 菜单ID集合
     */
    private List<Integer> permissionIds;

    /**
     * 角色ID集合
     */
    private List<Integer> roleIds;

    /**
     * 用户ID集合
     */
    private List<Long> userIds;
    /**
     * 成员列表
     */
    private String type;
    /**
     * 团队ID
     */
    private Long teamId;
}
