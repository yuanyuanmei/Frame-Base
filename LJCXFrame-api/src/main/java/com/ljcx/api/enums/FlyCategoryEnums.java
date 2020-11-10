package com.ljcx.api.enums;

/**
 * 飞行类型枚举
 */
public enum  FlyCategoryEnums {


    /**
     * 警示区
     */
    WARNING(0),
    /**
     * 授权区
     */
    AUTHORIZATION(1),
    /**
     * 限制区
     */
    RESTRICTED(2),
    /**
     * 增强警告区
     */
    ENHANCED_WARNING(3),
    /**
     * 未知区域
     */
    UNKNOWN(255);

    private final int val;

    FlyCategoryEnums(int val){
        this.val = val;
    }

    public int getVal(){
        return val;
    }


}
