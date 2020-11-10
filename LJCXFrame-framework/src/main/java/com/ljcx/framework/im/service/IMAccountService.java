package com.ljcx.framework.im.service;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.framework.im.dto.IMAccountDto;
import com.ljcx.framework.im.resp.IMResponse;

public interface IMAccountService {

    /**
     * 导入账号，注册账号
     */
    IMResponse accountImport(JSONObject data);

    /**
     * 批量操作账号
     */
    IMResponse accountBatch(IMAccountDto imAccountDto);

}
