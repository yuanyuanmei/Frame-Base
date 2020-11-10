package com.ljcx.common.gaode.constant;

/**
 * 高德地图常量
 */
public class MapConstant {

    /**
     * 服务器访问地址
     */
    public static final String GAODE_KET = "75149253d3560bd2f0f5ae6fa26996c0";

    /**
     *  高德API
     */
    public static final String GAODE_API = "https://restapi.amap.com/v3/";

    /**
     * 根据IP获取地理信息
     */
    public static final String GAODE_IP = GAODE_API + "ip";

    /**
     * 坐标地址相互转换
     */
    public static final String GAODE_ADDRESS = GAODE_API + "assistant/coordinate/convert";
}
