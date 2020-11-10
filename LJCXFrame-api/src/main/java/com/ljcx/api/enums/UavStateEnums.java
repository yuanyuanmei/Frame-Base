package com.ljcx.api.enums;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 飞机状态
 */
public enum  UavStateEnums {

    READY(0, "准备起飞"),
    TAKEOFF(1,"正在起飞"),
    HOLD(2,"正在盘旋"),
    MISSION(3,"正在按航线飞行"),
    RETURN_TO_LAUNCH(4,"返航"),
    LAND(5,"降落"),
    OFFBOARD(6,"外部接管中"),
    FOLLOW_ME(7,"跟随动态位置"),
    UNKNOWN(8,"未知模式");


    private final int code;
    private final String value;

    UavStateEnums(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    //枚举的变量必须为static静态
    public static List<UavStateEnums> list = Arrays.asList(values());

    //获得map集合
    public static List<Map> mapList() {
        List<Map> mapList = new ArrayList<>();
        Arrays.asList(values()).stream().forEach(item ->{
            Map<String,Object> map = new HashedMap();
            map.put("id",item.code);
            map.put("name",item.value);
            mapList.add(map);
        });
        return mapList;
    }

    public static String codeOf(int code){
        List<UavStateEnums> collect = list.stream().filter(item -> item.code == code).collect(Collectors.toList());
        String value = collect.size() > 0 ? collect.get(0).getValue() : "";
        return value;
    }
}
