package com.ljcx.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Serializable id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点是否打开
     */
    private boolean open = true;

    /**
     * data对象
     */
    private Object attr;

    /**
     * 上级id
     */
    private Integer pId;

    /**
     * 序号
     */
    private int sort;

    /**
     * 子集
     */
    private List<BaseTree> children;

}
