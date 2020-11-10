package com.ljcx.code.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * LjcxTeamInfo 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_team_info")
public class TeamInfoBean extends BaseEntity<Long> {

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
         * 房间号
         */
        private Integer roomId;

        private Long pid;

        private String path;

        private Long createUser = UserUtil.getCurrentUser().getId();

        private String imGroupId;

        private String imGroupName;

}
