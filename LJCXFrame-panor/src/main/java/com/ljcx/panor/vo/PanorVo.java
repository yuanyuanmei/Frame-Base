package com.ljcx.panor.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * LjcxCarInfo 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PanorVo implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer panoId;
        /**
         * 全景图标题
         */
        private String panoTitle;

        /**
         * 全景图在线地址
         */
        private String panoUrl;

        /**
         * 缩略图地址
         */
        private String panoThumb;

        /**
         * 地址
         */
        private String panoAddress;

        /**
         * 经度
         */
        private String lon;

        /**
         * 纬度
         */
        private String lat;

        /**
         * 发布人ID
         */
        private Integer userId;

        /**
         * 浏览次数
         */
        private Integer viewsNumber;

        /**
         * 发布时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date createdate;

        private Integer collectId;

        /**
         * 全景英文标题
         */
        private String panoTitleEn;

        /**
         * 全景英文地址
         */
        private String panoAddressEn;

}
