package com.ljcx.code.vo;

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
public class CarInfoVo implements Serializable {

        private static final long serialVersionUID = 1L;

    /**
     * id
         */
        private Long id;

        /**
         * 名称
         */
        private String name;


        /**
         * 所属团队
         */
        private String teamName;


        private Long createUser;

        @JSONField(format = "yyyy-MM-dd HH:mm")
        private Date createTime;

}
