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
@NotNull(message = "角色信息不能为空")
public class RoleDto extends PageDto{

    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;
    /**
     * 描述
     */
    private String memo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 权限集合
     */
    private List<Integer> permissionIds;

    /**
     * 用户ID集合
     */
    private List<Long> userIds;

}
