package com.ljcx.framework.sys.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysFileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private String fileName;

    private String url;

    private String suffix;

    /**
     * 缩略图上传文件路径
     */
    private String thumbPath;

    /**
     * url
     */
    private String thumbUrl;

}
