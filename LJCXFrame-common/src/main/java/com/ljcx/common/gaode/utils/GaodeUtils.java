
package com.ljcx.common.gaode.utils;
import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.gaode.constant.MapConstant;
import com.ljcx.common.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * IP地址
 *
 * @date 2017年3月8日 下午12:57:02
 */
@Slf4j
public class GaodeUtils {


    public static JSONObject getIPAddr(String ip)
    {
        RestTemplate client = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("key",MapConstant.GAODE_KET);
        //map.put("ip",ip);
        String param = MapUtils.Map2String(map);
        String url = MapConstant.GAODE_IP + "?"+ param;
        JSONObject ipResp = client.getForObject(url, JSONObject.class);
        return ipResp;
    }
	
}
