package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApkVersionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 版本名称，用于UI显示
     */
    private String versionName;

    /**
     * 版本号
     */
    private Integer versionCode;

    /**
     * 新版apk名称
     */
    private String apkName;

    /**
     * 是否强制更新，1是，0否
     */
    private Integer isForceUpdate;

    /**
     * 新版apk说明
     */
    private String memo;

    /**
     * appkey
     */
    private String appKey;

    /**
     * 下载地址
     */
    private String filePath;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
}
