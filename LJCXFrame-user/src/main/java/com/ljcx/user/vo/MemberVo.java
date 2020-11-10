package com.ljcx.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * MemberVo 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVo implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String account;
        /**
         * 手机号
         */
        private String phone;

        /**
         * 用户名
         */
        private String username;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 昵称
         */
        private String name;

        /**
         * 角色名称
         */
        private String roleName;

        /**
         * 头像
         */
        private String headerUrl;

        /**
         * 位置
         */
        private String position;

        /**
         * 上线状态
         */
        private int status = 0;

         /**
         * 是否通话 0.不通话，1.通话
         */
        private int isCalled;

        /**
         * 是否进入房间 0 未进入，1.进入
         */
        private int inRoom = 0;

        /**
         * 创建时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm")
        private Date createTime;

        /**
         * 所属团队
         */
        private String teamName;

        /**
         * 邮箱
         */
        private String email;



}
