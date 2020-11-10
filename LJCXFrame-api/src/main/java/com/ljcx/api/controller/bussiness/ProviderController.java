package com.ljcx.api.controller.bussiness;

import com.ljcx.framework.activemq.ActivemqProducer;
import com.ljcx.framework.sys.service.IGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 车controller
 */
@Api(value = "消息模块")
@RestController
@RequestMapping("/provider")
@Slf4j
public class ProviderController {


    @Autowired
    private IGenerator generator;

    @Autowired
    private ActivemqProducer producer;

    @RequestMapping("/send")
    public void send(String message){
        producer.sendQueue("丹妹1111",message);
    }


}
