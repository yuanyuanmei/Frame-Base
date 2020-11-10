package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
public class CarInfoVo  implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;
        /**
         * 位置
         */
        private String postion;

        /**
         * 上线状态
         */
        private Integer status;
        /**
         * 名称
         */
        private String name;


        private Long createUser;

        /**
         * 创建时间
         */
        @JSONField(format = "yyyy-MM-dd HH:mm")
        private Date createTime;

}
