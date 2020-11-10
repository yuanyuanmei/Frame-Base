package com.ljcx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.api.beans.FlyAreaBean;
import com.ljcx.api.dao.FlyAreaDao;
import com.ljcx.api.dto.FlyAreaDto;
import com.ljcx.api.service.FlyAreaService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FlyAreaServiceImpl extends ServiceImpl<FlyAreaDao, FlyAreaBean> implements FlyAreaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private FlyAreaDao flyAreaDao;


    @Override
    @Transactional
    public ResponseInfo save(List<FlyAreaDto> flyAreaDtos) {
        flyAreaDtos.forEach(item->{
            log.info("飞行区域打印日志,{}",item.toString());
            QueryWrapper searchWrapper = new QueryWrapper();
            searchWrapper.eq("fly_zone_id",item.getFlyZoneId());
            int count = flyAreaDao.selectCount(searchWrapper);
            if(count == 0){
                FlyAreaBean bean = generator.convert(item, FlyAreaBean.class);
                flyAreaDao.insert(bean);
                item.getChild().forEach(child->{
                    FlyAreaBean childBean = generator.convert(child, FlyAreaBean.class);
                    childBean.setParentId(bean.getId());
                    flyAreaDao.insert(childBean);
                });
            }
        });
        return new ResponseInfo().success("保存成功");
    }
}
