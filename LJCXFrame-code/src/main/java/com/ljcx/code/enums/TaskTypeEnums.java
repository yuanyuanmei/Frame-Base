package com.ljcx.code.enums;


import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 飞行区域
 */
public enum TaskTypeEnums {

    FLY(1, "飞行任务"),
    COLLECTION(2,"集合任务");


    private final int code;
    private final String value;

    TaskTypeEnums(int code, String value) {
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
    public static List<TaskTypeEnums> list = Arrays.asList(values());

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
        List<TaskTypeEnums> collect = list.stream().filter(item -> item.code == code).collect(Collectors.toList());
        String value = collect.size() > 0 ? collect.get(0).getValue() : "";
        return value;
    }

}
