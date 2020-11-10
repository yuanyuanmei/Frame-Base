package com.ljcx.framework.im.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.framework.im.common.IMUtil;
import com.ljcx.framework.im.dto.IMAccountDto;
import com.ljcx.framework.im.enums.RESTEnum;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMAccountService;
import org.springframework.stereotype.Service;

@Service
public class IMAccountServiceImpl implements IMAccountService {

    @Override
    public IMResponse accountImport(JSONObject data) {
        String s = IMUtil.sendUrl(RESTEnum.ACCOUNT_IMPORT, data.toJSONString());
        IMResponse imResponse = JSONObject.parseObject(s, IMResponse.class);
        return imResponse;
    }

    @Override
    public IMResponse accountBatch(IMAccountDto imAccountDto) {
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i<imAccountDto.getUsernames().length;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserID",imAccountDto.getUsernames()[i]);
            jsonArray.add(jsonObject);
        }
        String s = "";
        if(imAccountDto.getOpt().equals("check")){
            data.put("CheckItem",jsonArray);
            s = IMUtil.sendUrl(RESTEnum.ACCOUNT_CHECK, data.toJSONString());
        }else{
            data.put("DeleteItem",jsonArray);
            s = IMUtil.sendUrl(RESTEnum.ACCOUNT_DELETE, data.toJSONString());
        }

        IMResponse imResponse = JSONObject.parseObject(s, IMResponse.class);
        return imResponse;
    }
}
