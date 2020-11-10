package com.ljcx.framework.live.tencent.live;


import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ljcx.framework.live.common.AbstractClient;
import com.ljcx.framework.live.common.Credential;
import com.ljcx.framework.live.common.JsonResponseModel;
import com.ljcx.framework.live.common.exception.TencentCloudSDKException;
import com.ljcx.framework.live.common.profile.ClientProfile;
import com.ljcx.framework.live.tencent.live.models.AddLiveDomainRequest;
import com.ljcx.framework.live.tencent.live.models.AddLiveDomainResponse;

import java.lang.reflect.Type;

public class LiveClient extends AbstractClient {

    private static String endpoint = "live.tencentcloudapi.com";
    private static String version = "2019-11-27";

    /**
     * 构造client
     * @param credential 认证信息实例
     * @param region 产品地域
     */
    public LiveClient(Credential credential, String region) {
        this(credential, region, new ClientProfile());
    }

    /**
     * 构造client
     * @param credential 认证信息实例
     * @param region 产品地域
     * @param profile 配置实例
     */
    public LiveClient(Credential credential, String region,ClientProfile profile) {
        super(LiveClient.endpoint, LiveClient.version, credential, region);
    }

    /**
     *添加域名，一次只能提交一个域名。域名必须已备案。
     * @param req AddLiveDomainRequest
     * @return AddLiveDomainResponse
     * @throws TencentCloudSDKException
     */
    public AddLiveDomainResponse AddLiveDomain(AddLiveDomainRequest req) throws TencentCloudSDKException {
        JsonResponseModel<AddLiveDomainResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<AddLiveDomainResponse>>(){}.getType();
            rsp = gson.fromJson(this.internalRequest(req, "AddLiveDomain"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }



}
