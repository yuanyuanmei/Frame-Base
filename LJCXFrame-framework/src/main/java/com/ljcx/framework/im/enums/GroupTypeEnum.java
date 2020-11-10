package com.ljcx.framework.im.enums;

public enum  GroupTypeEnum {

    Private("Private"),
    Public("Public"),
    ChatRoom("ChatRoom"),
    AVChatRoom("AVChatRoom"),
    BChatRoom("BChatRoom");

    private final String value;

    GroupTypeEnum(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }

}
