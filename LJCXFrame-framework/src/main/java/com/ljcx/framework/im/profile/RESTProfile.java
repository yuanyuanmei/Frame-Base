package com.ljcx.framework.im.profile;

public class RESTProfile {

    /**
     * https 请求协议为 HTTPS，请求方式为 POST
     * console.tim.qq.com 请求域名 固定为console.tim.qq.com
     * ver	协议版本号 固定为v4
     * servicename 内部服务名，不同的 servicename 对应不同的服务类型
     * command 命令字，与 servicename 组合用来标识具体的业务功能
     * sdkappid App 在即时通信 IM 控制台获取的应用标识
     * identifier	用户名，调用 REST API 时必须为 App 管理员帐号
     * usersig 用户名对应的密码
     * random 标识当前请求的随机数参数 32位无符号整数随机数，取值范围0 - 4294967295
     * contenttype 请求格式 固定值为json
     */
    public static final String SAMPLE = "https://console.tim.qq.com/$ver/$servicename/$command?sdkappid=$SDKAppID&identifier=$identifier&usersig=$usersig&random=99999999&contenttype=json";

    /**
     * 域名
     */
    public static final String DOMAIN = "https://console.tim.qq.com/";

    /**
     * 管理员账号
     */
    public static final String ADMIN = "administrator";

    /**
     * SDKAPPid
     */
    public static final Long SDK_APP_ID = 1400285528L;
    /**
     * SDKAPPkey
     */
    public static final String SDK_APP_KEY = "e6b75d8c7647a0ce08707f2f52c276251da5e0d63448f37141a8ed4ab0de65bf";

}
