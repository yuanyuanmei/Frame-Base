package com.ljcx.framework.im.enums;

public enum RESTEnum {

    /**
     * 账号管理
     */
    ACCOUNT_IMPORT ("v4/im_open_login_svc/account_import"),
    ACCOUNT_DELETE ("v4/im_open_login_svc/account_delete"),
    ACCOUNT_CHECK ("v4/im_open_login_svc/account_check"),

    /**
     * 群组管理
     */
    GROUP_CREATE("v4/group_open_http_svc/create_group"),
    GROUP_ADD_MEMBER("v4/group_open_http_svc/add_group_member"),
    GROUP_SEND_MSG("v4/group_open_http_svc/send_group_msg"),
    GROUP_GET_MSG("v4/group_open_http_svc/group_msg_get_simple"),
    GROUP_DEL_MEMBER("v4/group_open_http_svc/delete_group_member");



    private final String url;

    RESTEnum(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

}
