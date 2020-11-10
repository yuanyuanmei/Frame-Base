package com.ljcx.code.dto;

import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.utils.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "团队信息不能为空")
public class TeamInfoDto extends PageDto {

    /**
     * id
     */
    @NotNull(message = "团队ID不能为空")
    private Long id;
    /**
     * 管理员ID
     */
    private Long userId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String memo;
    /**
     * id集合
     */
    private List<Long> ids;

    /**
     * mId集合
     */
    private List<Long> mIds;

    /**
     * 成员类型
     */
    private Integer mType;

    private Long pid;

    private String prePath;

    private Long createUser = UserUtil.getCurrentUser().getId();

    private List<Long> uavIds;

    private List<Long> carIds;

    private List<Long> memberIds;

}
