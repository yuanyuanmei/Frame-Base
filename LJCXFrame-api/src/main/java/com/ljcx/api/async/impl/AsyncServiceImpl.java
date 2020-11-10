package com.ljcx.api.async.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.async.AsyncService;
import com.ljcx.api.beans.PanoramaBean;
import com.ljcx.api.dao.PanoramaDao;
import com.ljcx.api.dto.PanoramaDto;
import com.ljcx.api.service.PanoramaService;
import com.ljcx.common.constant.UrlConstant;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.live.common.profile.HttpProfile;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.service.IGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private PanoramaDao panoramaDao;

    @Override
    @Async("asyncServiceExecutor")
    public void executeGenVtour() {
        PanoramaDto param = new PanoramaDto();
        param.setGenStatus(0);
        List<PanoramaBean> list = panoramaDao.listBean(param);
        if(list != null & list.size() > 0 ){
            for(PanoramaBean panor :list){
                RestTemplate client = new RestTemplate();
                String url = HttpProfile.REQ_HTTP+ UrlConstant.LOCALHOST+UrlConstant.PORT_KAPANO+UrlConstant.INTER_GENVTOUR;
                JSONObject result = client.postForEntity(url, panor, JSONObject.class).getBody();
                if(result != null && StringUtils.isNotEmpty(result.getString("url"))){
                    panor.setUrl(HttpProfile.REQ_HTTPS+UrlConstant.SERVER_URL+UrlConstant.PORT_SERVER_RESOURCE+"/"+result.getString("url")+"/tour.html");
                    panor.setGenStatus(1);
                    panoramaDao.updateById(panor);
                }
            }
        }
    }
}
