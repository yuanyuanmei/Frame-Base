package com.ljcx.user.beans;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


/**
* UserAccountBean 实体类
* Created by auto generator on Sat May 25 13:15:16 CST 2019.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "account_user_account")
public class UserAccountBean extends BaseEntity<Long> {
        /**
        * 账号
        */
        private String account;
        /**
         * 帐号类型 1.普通账号 2.手机帐号 3,邮箱帐号
         */
        //@NotNull(message = "帐号类型不能为空")
        private Integer type;
        /**
        * 密码
        */
        private String password;
        /**
        * 盐值
        */
        private String salt;
        /**
        * 关联用户ID
        */
        private Long userId;

        /**
         * 最后一次登录IP
         */
        private String lastLoginIp;
        /**
         * 最后一次登录时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm")
        private Date lastLoginTime;

        /**
         * 关联用户基础类
         */
        @TableField(exist = false)
        private UserBaseBean userBaseBean;

        /**
         * 方便构造对象
         */
        public UserAccountBean(String account, String password, Integer type){
                this.account = account;
                this.password = password;
                this.type = type;
        }

}