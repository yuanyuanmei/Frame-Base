package com.ljcx.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Long value;
}
