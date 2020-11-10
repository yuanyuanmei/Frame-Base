package com.ljcx.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

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
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 总时长
     */

    private Double hours;

    /**
     * 总里程
     */
    private Double mileage;

    /**
     * 角色名称列表
     */
    private List<String> roleName;

    private String userSig;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上传人横坐标
     */
    private String lng;

    /**
     * 上传人纵坐标
     */
    private String lat;
}
