$(function(){
    var loginInfo = {
        'sdkAppID': '1400285528', //用户所属应用id,必填
        // 'accountType': accountType, //用户所属应用帐号类型, 已废弃
        'identifier': '', //当前用户ID,必须是否字符串类型，必填
        'userSig': '',
        //当前用户身份凭证，必须是字符串类型，必填
        'identifierNick': "丹妹", //当前用户昵称，不用填写，登录接口会返回用户的昵称，如果没有设置，则返回用户的id
        'headurl': 'img/me.jpg' //当前用户默认头像，选填，如果设置过头像，则可以通过拉取个人资料接口来得到头像信息
    };

    //im js
    //sdk登录
    //当前用户身份

    //监听事件
    var listeners = {
        "onConnNotify": onConnNotify, //监听连接状态回调变化事件,必填
        "onMsgNotify": onMsgNotify, //监听新消息(私聊，普通群(非直播聊天室)消息，全员推送消息)事件，必填
        "onLongPullingNotify": function (data) {
            console.debug('onLongPullingNotify', data)
        }
    };
    var isAccessFormalEnv = true; //是否访问正式环境

    var isLogOn = false; //是否开启sdk在控制台打印日志

    //初始化时，其他对象，选填
    var options = {
        'isAccessFormalEnv': isAccessFormalEnv, //是否访问正式环境，默认访问正式，选填
        'isLogOn': isLogOn //是否开启控制台打印日志,默认开启，选填
    }

    Ajax.postJson("/im/account/userSign", null, JSON.stringify({"isFail":0}), "", function (result) {
        if (result.code == 200){
            loginInfo.identifier = result.data.username;
            loginInfo.userSig = result.data.userSig;
            webimLogin();
        }
    }, function (XMLHttpRequest, textStatus, errorThrown) {
    });


    //监听连接状态回调变化事件
    var onConnNotify = function (resp) {
        var info;
        switch (resp.ErrorCode) {
            case webim.CONNECTION_STATUS.ON:
                webim.Log.warn('建立连接成功: ' + resp.ErrorInfo);
                break;
            case webim.CONNECTION_STATUS.OFF:
                info = '连接已断开，无法收到新消息，请检查下你的网络是否正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            case webim.CONNECTION_STATUS.RECONNECT:
                info = '连接状态恢复正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            default:
                webim.Log.error('未知连接状态: =' + resp.ErrorInfo);
                break;
        }
    };

    function webimLogin() {
        webim.login(
            loginInfo, listeners, options,
            function(resp) {
                //successCB && successCB(resp);
                console.log("im登录状态 ="+resp.ErrorInfo);
            },
            function(err) {
                webim.Log.error('XXX 登录错误 XXX: =' + err.ErrorInfo);
                console.log(err.ErrorInfo);
                //alert(err.ErrorInfo);
                //errorCB && errorCB(err);
            }
        );
    }

});