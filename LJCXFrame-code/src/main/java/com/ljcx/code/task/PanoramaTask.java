package com.ljcx.code.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljcx.code.beans.PanoramaBean;
import com.ljcx.code.dao.PanoramaDao;
import com.ljcx.code.dto.PanoramaDto;
import com.ljcx.common.constant.UrlConstant;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.common.utils.html.EscapeUtil;
import com.ljcx.common.utils.http.HttpUtils;
import com.ljcx.framework.live.common.profile.HttpProfile;
import com.ljcx.framework.live.common.profile.LiveProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("panoramaTask")
@Slf4j
public class PanoramaTask
{
    @Autowired
    private PanoramaDao panoramaDao;
    /**
     * 生成全景图
     */
    public void genVtour()
    {
        PanoramaDto param = new PanoramaDto();
        param.setGenStatus(0);
        List<PanoramaBean> list = panoramaDao.listBean(param);
        if(list != null & list.size() > 0 ){
            for(PanoramaBean panor :list){
                RestTemplate client = new RestTemplate();
                String url = HttpProfile.REQ_HTTP+ UrlConstant.LOCALHOST+UrlConstant.PORT_KAPANO+UrlConstant.INTER_GENVTOUR;
                JSONObject result = client.postForEntity(url, panor, JSONObject.class).getBody();
                if(result != null && StringUtils.isNotEmpty(result.getString("url"))){
                    log.info("全景图生成成功！！！，{}",result.getString("url"));
                    panor.setUrl(HttpProfile.REQ_HTTPS+UrlConstant.SERVER_URL+UrlConstant.PORT_SERVER_RESOURCE+"/"+result.getString("url")+"/tour.html");
                    panor.setGenStatus(1);
                    panoramaDao.updateById(panor);
                }
            }
        }
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
