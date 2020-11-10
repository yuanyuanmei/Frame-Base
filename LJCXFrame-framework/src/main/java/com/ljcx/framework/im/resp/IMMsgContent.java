package com.ljcx.framework.im.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class IMMsgContent {

    /**
     * 文本消息
     */
    @JSONField(name = "Text")
    private String Text;

    /**
     * 图片消息
     */
    //文件序列号
    @JSONField(name = "UUID")
    private String UUID;

    //图片格式
    @JSONField(name = "ImageFormat")
    private Integer ImageFormat;

    @JSONField(name = "ImageInfoArray")
    private List<IMImage> ImageInfoArray;

    /**
     * 视频消息
     */

    //视频下载地址
    @JSONField(name = "VideoUrl")
    private String VideoUrl;

    //视频数据大小
    @JSONField(name = "VideoSize")
    private Integer VideoSize;

    //视频时长
    @JSONField(name = "VideoSecond")
    private Integer VideoSecond;

    //视频格式
    @JSONField(name = "VideoFormat")
    private String VideoFormat;

    //视频下载方式标记
    @JSONField(name = "VideoDownloadFlag")
    private Integer VideoDownloadFlag;

    //视频缩略图下载地址
    @JSONField(name = "ThumbUrl")
    private String ThumbUrl;

    //缩略图大小
    @JSONField(name = "ThumbSize")
    private Integer ThumbSize;

    //缩略图宽度
    @JSONField(name = "ThumbWidth")
    private Integer ThumbWidth;

    //缩略图高度
    @JSONField(name = "ThumbHeight")
    private Integer ThumbHeight;

    //缩略图格式
    @JSONField(name = "ThumbFormat")
    private String ThumbFormat;

    //视频缩略图下载方式标记
    @JSONField(name = "ThumbDownloadFlag")
    private Integer ThumbDownloadFlag;

    /**
     * 音频消息
     */

    //语音下载地址
    @JSONField(name = "Url")
    private String Url;

    //语音数据大小
    @JSONField(name = "Size")
    private Integer Size;

    //语音时长
    @JSONField(name = "Second")
    private Integer Second;

    //语音下载方式标记
    @JSONField(name = "Download_Flag")
    private Integer Download_Flag;








}
