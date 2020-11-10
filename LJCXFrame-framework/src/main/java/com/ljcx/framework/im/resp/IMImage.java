package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 成员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IMImage {

    /**
     * 	图片类型： 1-原图，2-大图，3-缩略图。
     */
    @JSONField(name = "Type")
    private Integer Type;

    @JSONField(name = "Size")
    private Integer Size;

    @JSONField(name = "Width")
    private Integer Width;

    @JSONField(name = "Height")
    private Integer Height;

    @JSONField(name = "URL")
    private String URL;
}
