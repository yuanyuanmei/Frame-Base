package com.ljcx.panor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.utils.RedisUtils;
import com.ljcx.common.utils.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.security.Security;

/***
 *
 * @ClassName WechatUtil
 * @Description 微信相关工具类
 *
 */
@Component
public class WechatUtil {
    public static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    public static final String KEY_WECHAT = "wechat";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_WX_SESSION = "session";
    public static final String KEY_EXPIRES_IN = "expires_in";
    public static final String REQUEST_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

    public static final String OPENID = "openid";
    public static final String SESSION_KEY = "session_key";
    public static final String REQUEST_SESSION = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String WX_APPID = "wxa6f1289a9a43ff8a";
    public static final String WX_SECRET = "03236b6743911ecee394300d92d5fdfe";

    private static RedisUtils RedisUtil;

    @Autowired
    private RedisUtils res;

    @PostConstruct
    public void beforeInit() {
        RedisUtil = res;
    }
    /**
     * 小程序编码 一键报警
     */
    public static final String ONE_KEY_SOS = "ONE_KEY_SOS";

    /**
     * 根据微信 appid 和 secret 从缓存中获取微信 access_token。如果缓存中不存在则发送http请求到微信中获取
     *
     * @param appid
     * @param secret
     * @return
     */
    public static String getWxAccessToken(String appid, String secret) {
        String accessToken = RedisUtil.get(KEY_WECHAT + KEY_ACCESS_TOKEN + appid);
        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        } else {
            return getWxHttpAccessToken(appid, secret);
        }
    }

    /**
     * @param appid
     * @param secret
     * @return
     * @author Chencs
     * @date 2018年8月31日 下午3:30:05
     */
    public static String getWxHttpAccessToken(String appid, String secret) {
        String param = "grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String resultStr = HttpUtils.sendGet(REQUEST_ACCESS_TOKEN, param);
        logger.info(resultStr);
        JSONObject result = JSON.parseObject(resultStr);
        if (result.containsKey(KEY_ACCESS_TOKEN)) {
            RedisUtil.set(KEY_WECHAT + KEY_ACCESS_TOKEN + appid, result.getString(KEY_ACCESS_TOKEN), result.getInteger(KEY_EXPIRES_IN));
            return result.getString(KEY_ACCESS_TOKEN);
        } else {
            logger.error("get wechat access_token error ! response is: " + resultStr);
            throw new RuntimeException("get wechat access_token error. response is: " + resultStr);
        }
    }

    /***
     * @param jsCode
     *            登录时获取的 code,有效期五分钟
     * @return
     */
    public static String getOpenId(String jsCode) {
        String param = REQUEST_SESSION + "grant_type=authorization_code&appid=" + WX_APPID + "&secret=" + WX_SECRET + "&js_code=" + jsCode;

        String resultStr = HttpUtils.sendGet(REQUEST_SESSION,param);
        logger.info(resultStr);
        JSONObject result = JSON.parseObject(resultStr);
        if (result.containsKey(SESSION_KEY)) {
            RedisUtil.set(KEY_WECHAT + KEY_WX_SESSION + result.getString(OPENID), result.getString(SESSION_KEY));
            return result.getString(OPENID);
        } else {
            logger.error("get wechat jscode2session error ! response is: " + resultStr);
            throw new RuntimeException("get wechat jscode2session error. response is: " + resultStr);
        }

    }


    /*public static String decrypt(String openId, String iv, String encryptedData) throws Exception {
        String sessionKey = RedisUtil.get(KEY_WECHAT + KEY_WX_SESSION + openId);
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

        byte[] resultByte = AESUtils.decrypt(sessionKey, iv, encryptedData, cipher);
        if (null != resultByte && resultByte.length > 0) {
            return new String(resultByte, "UTF-8");
        } else {
            return null;
        }
    }*/

    public static void main(String[] args) {
        //System.out.println(getWxAccessToken("wx9282d39af6b98610", "64d609df9f805b9b0f5bef784d93f117"));
//        System.out.println(getOpenId("023guYZo0pcyEk1nxXZo0eyRZo0guYZB"));
        //"session_key":"nGDP4Fq87s+G4p1BqS195A==","openid":"ozt3F5ECMW44VkiRekqfCZQHgEJE"
    }

}
