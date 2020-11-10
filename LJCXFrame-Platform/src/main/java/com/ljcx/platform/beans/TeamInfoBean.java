package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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

        private Integer roomId;

        private Long createUser;


        private String imGroupId;

        private String imGroupName;

        /**
         * 上级id
         */
        @TableField(value="pid")
        private Long pid;

        /**
         * 路径
         */
        private String path;

}
