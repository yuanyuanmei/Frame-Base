package com.ljcx.common.enums;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求状态码
 */
public enum LJHttpStatus {


    //正常
    OK(200, "返回成功"),
    FAILED(201,"返回失败"),
    VALID_ERROR(400, "参数异常"),
    PERMS_NOT_ENOUGH(401, "权限不足"),
    SYSTEM_ERROR(500, "系统异常");


    private final int code;
    private final String value;

    LJHttpStatus(int code, String value) {
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
    public static List<LJHttpStatus> list = Arrays.asList(values());

    public static String codeOf(int code){
        List<LJHttpStatus> collect = list.stream().filter(item -> item.code == code).collect(Collectors.toList());
        String value = collect.size() > 0 ? collect.get(0).getValue() : "";
        return value;
    }

}
