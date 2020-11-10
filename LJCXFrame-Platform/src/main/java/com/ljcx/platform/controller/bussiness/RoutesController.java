package com.ljcx.platform.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.base.BaseEntity;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.platform.beans.RoutesFlightBean;
import com.ljcx.platform.dto.FlyAreaDto;
import com.ljcx.platform.service.FlyAreaService;
import com.ljcx.platform.service.LayerService;
import com.ljcx.platform.service.PanoramaService;
import com.ljcx.platform.vo.LayerBeanVo;
import com.ljcx.platform.vo.LayerVo;
import com.ljcx.platform.vo.PanoramaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 图层控制层
 */
@RestController
@RequestMapping("/routes")
@Slf4j
public class RoutesController {

//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    /**
//     * 根据category类型获取图层信息
//     * @return
//     */
//    @PostMapping("/records/save")
//    public ResponseInfo saveRecords() {
//        RoutesFlightBean bean = new RoutesFlightBean();
//        bean.setRecordsId(1l);
//        bean.setRoutes("12131231231231");
//        mongoTemplate.insert(bean);
//        return new ResponseInfo().success("傻瓜我们都一样");
//    }
//
//    @PostMapping("/records/get")
//    public ResponseInfo getRecords() {
//        Query query = new Query(Criteria.where("recordsId").is(1));
//        List<RoutesFlightBean> routesFlightBeans = mongoTemplate.find(query, RoutesFlightBean.class);
//        routesFlightBeans.stream().forEach(item->{
//            Query upQ = new Query(Criteria.where("id").is(item.getId()));
//            Update update = new Update().set("routes","擦浪嘿哟").set("recordsId",52l);
//            mongoTemplate.updateFirst(upQ,update,RoutesFlightBean.class);
//        });
//        return new ResponseInfo(routesFlightBeans);
//    }



}
