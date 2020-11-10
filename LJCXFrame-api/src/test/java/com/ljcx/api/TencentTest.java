package com.ljcx.api;

import com.ljcx.framework.live.common.Credential;
import com.ljcx.framework.live.common.exception.TencentCloudSDKException;
import com.ljcx.framework.live.common.profile.AppProfile;
import com.ljcx.framework.live.common.profile.ClientProfile;
import com.ljcx.framework.live.common.profile.HttpProfile;
import com.ljcx.framework.live.tencent.trtc.TrtcClient;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomRequest;
import com.ljcx.framework.live.tencent.trtc.models.DissolveRoomResponse;
import com.ljcx.framework.live.tencent.usersign.TLSSigAPIv2;
import org.junit.Test;

public class TencentTest {

    @Test
    public void test1(){
        //凭证
        Credential credential = new Credential(AppProfile.SECRET_ID,AppProfile.SECRET_KEY);
        //配置数据实例
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(new HttpProfile());
        TrtcClient client = new TrtcClient(credential,"ap-guangzhou",clientProfile);

        DissolveRoomRequest disRoomReq = new DissolveRoomRequest();
        disRoomReq.setRoomId(11L);
        disRoomReq.setSdkAppId(AppProfile.AUDIO_VIDEO_SDK_APP_ID);

        try {
            DissolveRoomResponse response = client.DissolveRoom(disRoomReq);
            System.out.println(DissolveRoomResponse.toJsonString(response));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGenSig() {
        TLSSigAPIv2 api = new TLSSigAPIv2(AppProfile.AUDIO_VIDEO_SDK_APP_ID,"e6b75d8c7647a0ce08707f2f52c276251da5e0d63448f37141a8ed4ab0de65bf" );
        System.out.print(api.genSig("123", 180*86400));
    }


    @Test
    public void testGenSigWithUserBug() {
        TLSSigAPIv2 api = new TLSSigAPIv2(AppProfile.AUDIO_VIDEO_SDK_APP_ID,"e6b75d8c7647a0ce08707f2f52c276251da5e0d63448f37141a8ed4ab0de65bf" );
        System.out.println(api.genSigWithUserBuf("1", 180*86400, "1".getBytes()));
    }

}
