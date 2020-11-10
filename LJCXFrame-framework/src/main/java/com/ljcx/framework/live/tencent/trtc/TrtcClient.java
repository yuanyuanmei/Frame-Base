package com.ljcx.framework.live.tencent.trtc;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.ljcx.framework.live.common.AbstractClient;
import com.ljcx.framework.live.common.Credential;
import com.ljcx.framework.live.common.JsonResponseModel;
import com.ljcx.framework.live.common.exception.TencentCloudSDKException;
import com.ljcx.framework.live.common.profile.ClientProfile;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomRequest;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomResponse;
import com.ljcx.framework.live.tencent.trtc.models.KickOutUserRequest;
import com.ljcx.framework.live.tencent.trtc.models.KickOutUserResponse;

import java.lang.reflect.Type;

public class TrtcClient extends AbstractClient {

    private static String endpoint = "trtc.tencentcloudapi.com";
    private static String version = "2019-07-22";

    /**
     * 构造client
     * @param credential 认证信息实例
     * @param region	产品地域
     */
    public TrtcClient(Credential credential, String region) {
        this(credential, region, new ClientProfile());
    }

    /**
     * 构造client
     * @param credential 认证信息实例
     * @param region	产品地域
     * @param profile 配置实例
     */
    public TrtcClient(Credential credential, String region, ClientProfile profile) {
        super(TrtcClient.endpoint, TrtcClient.version, credential, region, profile);
    }

    /**
     *接口说明：把房间所有用户从房间踢出，解散房间。支持所有平台，Android、iOS、Windows 和 macOS 需升级到 TRTC SDK 6.6及以上版本。
     * @param req DissolveRoomRequest
     * @return DissolveRoomResponse
     * @throws TencentCloudSDKException
     */
    public DissolveRoomResponse DissolveRoom(DissolveRoomRequest req) throws TencentCloudSDKException {
        JsonResponseModel<DissolveRoomResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<DissolveRoomResponse>>() {
            }.getType();
            rsp  = gson.fromJson(this.internalRequest(req, "DissolveRoom"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }

    /**
     *接口说明：将用户从房间踢出。支持所有平台，Android、iOS、Windows 和 macOS 需升级到 TRTC SDK 6.6及以上版本。
     * @param req KickOutUserRequest
     * @return KickOutUserResponse
     * @throws TencentCloudSDKException
     */
    public KickOutUserResponse KickOutUser(KickOutUserRequest req) throws TencentCloudSDKException{
        JsonResponseModel<KickOutUserResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<KickOutUserResponse>>() {
            }.getType();
            rsp  = gson.fromJson(this.internalRequest(req, "KickOutUser"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }

}
