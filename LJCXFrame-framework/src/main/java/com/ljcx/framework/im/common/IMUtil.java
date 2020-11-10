package com.ljcx.framework.im.common;

import com.ljcx.common.utils.MapUtils;
import com.ljcx.common.utils.http.HttpUtils;
import com.ljcx.framework.im.enums.RESTEnum;
import com.ljcx.framework.im.profile.RESTProfile;
import com.ljcx.framework.live.tencent.LiveUtil;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.Random;

public class IMUtil {

    public static String sendUrl(RESTEnum restEnum,String data){
        Random random = new Random();
        random.nextInt(999999999);
        String userSign = LiveUtil.genSig(RESTProfile.ADMIN);
        Map<String,String> param = new HashedMap();
        param.put("sdkappid",RESTProfile.SDK_APP_ID.toString());
        param.put("identifier",RESTProfile.ADMIN);
        param.put("usersig",userSign);
        param.put("random",random.toString());
        param.put("contenttype","json");
        String paramUrl = MapUtils.Map2String(param);
        String mainUrl = RESTProfile.DOMAIN+restEnum.getUrl();
        return HttpUtils.sendPost(mainUrl,paramUrl,data);

    }
}
