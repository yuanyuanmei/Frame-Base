package com.ljcx.framework.live.tencent;

import com.ljcx.framework.live.common.Credential;
import com.ljcx.framework.live.common.exception.TencentCloudSDKException;
import com.ljcx.framework.live.common.profile.AppProfile;
import com.ljcx.framework.live.common.profile.ClientProfile;
import com.ljcx.framework.live.common.profile.HttpProfile;
import com.ljcx.framework.live.common.profile.LiveProfile;
import com.ljcx.framework.live.tencent.trtc.TrtcClient;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomRequest;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomResponse;
import com.ljcx.framework.live.tencent.usersign.TLSSigAPIv2;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 直播工具类
 */
public class LiveUtil {

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 根据用户ID生成用户签名
     * @param id
     * @return
     */
    public static String genSig(Long id) {
        TLSSigAPIv2 api = new TLSSigAPIv2(AppProfile.AUDIO_VIDEO_SDK_APP_ID, AppProfile.AUDIO_VIDEO_SDK_APP_KEY);
        return api.genSig(id.toString(), AppProfile.SIGN_EXPIRE_TIME);
    }

    /**
     * 根据用户ID生成用户签名
     * @param id
     * @return
     */
    public static String genSig(String id) {
        TLSSigAPIv2 api = new TLSSigAPIv2(AppProfile.AUDIO_VIDEO_SDK_APP_ID, AppProfile.AUDIO_VIDEO_SDK_APP_KEY);
        return api.genSig(id, AppProfile.SIGN_EXPIRE_TIME);
    }

    /**
     * 根据用户ID和bug生成用户签名
     * @param id
     * @param bug
     * @return
     */
    public static String genSigWithUserBug(Long id,String bug) {
        TLSSigAPIv2 api = new TLSSigAPIv2(AppProfile.AUDIO_VIDEO_SDK_APP_ID, AppProfile.AUDIO_VIDEO_SDK_APP_KEY);
        return api.genSigWithUserBuf(id.toString(), AppProfile.SIGN_EXPIRE_TIME, bug.getBytes());
    }

    /**
     * 解散房间
     * @param roomId 房间号
     * 返回requestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @return
     */
    public static String dissolveRoom(Long roomId){
        //凭证
        Credential credential = new Credential(AppProfile.SECRET_ID,AppProfile.SECRET_KEY);
        //配置数据实例
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(new HttpProfile());
        TrtcClient client = new TrtcClient(credential,"ap-guangzhou",clientProfile);

        DissolveRoomRequest disRoomReq = new DissolveRoomRequest();
        disRoomReq.setRoomId(roomId);
        disRoomReq.setSdkAppId(AppProfile.AUDIO_VIDEO_SDK_APP_ID);

        try {
            DissolveRoomResponse response = client.DissolveRoom(disRoomReq);
            return DissolveRoomResponse.toJsonString(response);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 推流生成加密字符串
     * @param streamName
     * @return
     */
    public static String getSafeUrl(String streamName){
        //过期时间要为16进制，默认为2小时过期
        Date date = new Date();
        date.setTime(System.currentTimeMillis()+ LiveProfile.EXPIRE_TIME);
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return getSafeUrl(LiveProfile.PUSH_KEY,streamName,getUnixTime(dateStr));
    }
    /**
     * 生成推流地址
     * KEY+ streamName + txTime
     * 过期时间要求16进制
     */
    private static String getSafeUrl(String key, String streamName, long txTime) {
        String input = new StringBuilder().
                append(key).
                append(streamName).
                append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return txSecret == null ? "" :
                new StringBuilder().
                        append("txSecret=").
                        append(txSecret).
                        append("&").
                        append("txTime=").
                        append(Long.toHexString(txTime).toUpperCase()).
                        toString();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    /**
     * 获取unix时间戳
     * @return
     * @throws Exception
     */
    public static Long getUnixTime (String dateStr) {

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long epoch = df.parse(dateStr).getTime();

            return epoch/1000;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0L;
    }
}
