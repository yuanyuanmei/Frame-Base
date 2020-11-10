package com.ljcx.platform.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljcx.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * LjcxCarInfo 实体类
 * Created by auto generator on Thu May 30 20:50:32 CST 2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "ljcx_uav_info")
public class UavInfoBean extends BaseEntity<Integer> {

        /**
         * 序列号
         */
        private String no;
        /**
         * 型号
         */
        private String model;

        /**
         * 名称
         */
        private String name;


        private Long createUser;
}
