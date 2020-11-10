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
import java.util.List;


/**
* UserBaseBean 实体类
* Created by auto generator on Sat May 25 13:15:16 CST 2019.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "account_user_base")
public class UserBaseBean extends BaseEntity<Long> {
        /**
        * 邮箱
        */
        private String email;
        /**
        * 账户头像
        */
        private String headerUrl;
        /**
        * 手机号
        */
        private String phone;
        /**
        * 用户类型(1.超级管理员,2.普通用户)
        */
        private Integer type;
        /**
         * 状态(1.启用，2.禁用)
         */
        private Integer status;

        /**
        * 用户名
        */        
        private String username;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 总时长
         */

        private Long hours;

        /**
         * 总里程
         */
        private Double mileage;

        /**
         * 用户签名
         */
        @JSONField(serialize = false)
        private String userSig;

        /**
         * 签名过期时间
         */
        @JSONField(serialize = false)
        private Date signExpireTime;

        /**
         * 0.未注册，1.已注册
         */
        private Integer imRegistered;

        /**
         * 上传人横坐标
         */
        @TableField(exist = false)
        private String lng;

        /**
         * 上传人纵坐标
         */
        @TableField(exist = false)
        private String lat;

//        /**
//         * 角色列表
//         */
//        @TableField(exist = false)
//        private List<SysRoleBean> roleBeans;

        public UserBaseBean(String username,String nickname){
            super();
            this.username = username;
            this.nickname = nickname;
        }


}
