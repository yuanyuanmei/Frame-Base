package com.ljcx.framework.im.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum IMMessageEnum {

    TIMTextElem(1, "TIMTextElem"),
    TIMLocationElem(2,"TIMLocationElem"),
    TIMFaceElem(3, "TIMFaceElem"),
    TIMCustomElem(4, "TIMCustomElem"),
    TIMSoundElem(5, "TIMSoundElem"),
    TIMImageElem(6, "TIMImageElem"),
    TIMFileElem(7, "TIMFileElem"),
    TIMVideoFileElem(8, "TIMVideoFileElem");


    private final int code;
    private final String value;

    IMMessageEnum(int code, String value) {
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
    public static List<IMMessageEnum> list = Arrays.asList(values());

    public static String codeOf(int code){
        List<IMMessageEnum> collect = list.stream().filter(item -> item.code == code).collect(Collectors.toList());
        String value = collect.size() > 0 ? collect.get(0).getValue() : "";
        return value;
    }
}
