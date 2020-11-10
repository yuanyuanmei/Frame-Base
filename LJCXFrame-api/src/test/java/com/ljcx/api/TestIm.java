package com.ljcx.api;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.framework.im.common.IMUtil;
import com.ljcx.framework.im.enums.RESTEnum;
import org.junit.jupiter.api.Test;

public class TestIm {

    @Test
    public void test1(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier","test");
        jsonObject.put("Nick","test");
        jsonObject.put("FaceUrl","http://www.qq.com");
        IMUtil.sendUrl(RESTEnum.ACCOUNT_IMPORT,jsonObject.toJSONString());
    }


}
