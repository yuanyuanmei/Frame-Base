package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.api.enums.UavStateEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.dozer.Mapping;

import java.util.Date;


/**
 * LjcxCarInfo 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UavInfoVo {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long teamId;

        /**
         * 序列号
         */
        private String no;
        /**
         * 型号
         */
        private String model;
        /**
         * 状态
         * 1.下线，2.上线，3.异常
         */
        private Integer status = 0;

        /**
         * 名称
         */
        private String name;
        /**
         * 速度
         */
        private Double speed;
        /**
         * 高度
         */
        private Double high;
        /**
         * 航向
         */
        private String course;
        /**
         * 电池
         */
        private Integer electricity;

        /**
         * 网络
         */
        private String network;

        /**
         * 飞行状态
         */
        private Integer inFlight;

        /**
         * 飞行状态值
         */
        private String flightName;


        private String position;

        private Long createUser;

        /**
         * 创建时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm")
        private Date createTime;

        public String getFlightName() {
                if(inFlight != null){
                        return UavStateEnums.codeOf(inFlight);
                }
                return "未知模式";
        }

        /**
         * play 直播，close未直播
         */
        private String action = "close";

        /**
         * rmtp播放地址
         */
        private String rtmpPlayAddress;

        /**
         * fly播放地址
         */
        private String flyPlayAddress;

        /**
         * hls播放地址
         */
        private String hlsPlayAddress;

        /**
         * 能否直播 0 不可以， 1.可以
         */
        private int canLive = 0;

        /**
         * 电池健康
         */
        private int batteryHealth;

        /**
         * 任务飞行次数
         */
        private long taskNum;

        /**
         * 任务航行时长
         */
        private Long taskHours;

        /**
         * 航向
         */
        private double yaw;

        /**
         * 时间戳
         */
        private Long createTimeStamp;

        private Long recordsId;

}
