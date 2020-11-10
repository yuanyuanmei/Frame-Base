package com.ljcx.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto implements Serializable {

    private static final long serialVersionUID = 4043470238789599973L;

    private String key;

    private int pageNum = 1;

    private int pageSize = 10;

}
