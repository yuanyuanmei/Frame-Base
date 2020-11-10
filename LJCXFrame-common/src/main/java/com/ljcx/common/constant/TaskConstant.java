package com.ljcx.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TaskConstant {

    //创建任务
    CREATE_TASK("CREATE_TASK","创建任务"),
    //编辑任务
    EDIT_TASK("EDIT_TASK","编辑任务"),
    //开始任务
    START_TASK("START_TASK","开始任务"),
    //开始飞行
    START_FLY("START_FLY","开始飞行"),
    //结束飞行
    END_FLY("END_FLY","结束飞行"),
    //上传成果
    UPLOAD_RESULT("UPLOAD_RESULT","上传成果"),
    //返航
    TURN_BACK("TURN_BACK","返航"),
    //取消任务
    CANCEL_TASK("CANCEL_TASK","取消任务"),
    //完成任务
    END_TASK("END_TASK","完成任务"),
    //未知
    OTHER("OTHER","未知");

    private final String code;
    private final String value;

    TaskConstant(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    //枚举的变量必须为static静态
    public static List<TaskConstant> list = Arrays.asList(values());

    public static TaskConstant getByCode(String type){
        for (TaskConstant constants : values()) {
            if (constants.getCode().equalsIgnoreCase(type)) {
                return constants;
            }
        }
        return OTHER;
    }

    public static String codeOf(String code){
        List<TaskConstant> collect = list.stream().filter(item -> item.code.equals(code)).collect(Collectors.toList());
        String value = collect.size() > 0 ? collect.get(0).getValue() : "";
        return value;
    }
}
