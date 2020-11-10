package com.ljcx.platform.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserDto implements Serializable {

    private static final long serialVersionUID = 4043470238789599973L;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;


}
