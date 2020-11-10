package com.ljcx.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.platform.beans.FlyAreaBean;
import com.ljcx.platform.dao.FlyAreaDao;
import com.ljcx.platform.dto.FlyAreaDto;
import com.ljcx.platform.service.FlyAreaService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.vo.FlyAreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlyAreaServiceImpl extends ServiceImpl<FlyAreaDao, FlyAreaBean> implements FlyAreaService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private FlyAreaDao flyAreaDao;


    @Override
    public List<FlyAreaVo> getListByCategory(FlyAreaDto flyAreaDto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category",flyAreaDto.getCategory());
        queryWrapper.eq("parent_id",0);
        List<FlyAreaVo> list = generator.convert(flyAreaDao.selectList(queryWrapper),FlyAreaVo.class);
        for(FlyAreaVo item: list){
            QueryWrapper childWrapper = new QueryWrapper();
            childWrapper.eq("parent_id",item.getId());
            //限制区要做区别判断
            if(flyAreaDto.getCategory() == 2){
                if(flyAreaDto.getMaximumFlightHeight() > 0){
                    childWrapper.gt("maximum_flight_height",0);
                }else{
                    childWrapper.eq("maximum_flight_height",0);
                }
            }
            List<FlyAreaVo> childVoList = generator.convert(flyAreaDao.selectList(childWrapper), FlyAreaVo.class);
            item.setChild(childVoList);
        }
        if(flyAreaDto.getCategory() == 2 && flyAreaDto.getMaximumFlightHeight() > 0){
            list = list.stream().filter(item-> (item.getFlyZoneType().equals("CIRCLE") && item.getMaximumFlightHeight() > 0)
                    || (item.getFlyZoneType().equals("POLY") && item.getChild().size() > 0)
            ).collect(Collectors.toList());
        }else if(flyAreaDto.getCategory() == 2 && flyAreaDto.getMaximumFlightHeight() == 0){
            list = list.stream().filter(item-> (item.getFlyZoneType().equals("CIRCLE") && item.getMaximumFlightHeight() == 0)
                    || (item.getFlyZoneType().equals("POLY") && item.getChild().size() > 0)
            ).collect(Collectors.toList());
        }
        return list;
    }
}
