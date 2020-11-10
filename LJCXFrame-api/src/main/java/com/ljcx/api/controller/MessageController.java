package com.ljcx.api.controller;

import com.ljcx.common.utils.ResponseInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提示信息控制层
 */
@RestController
public class MessageController {

    @GetMapping("/login.html")
    public ResponseInfo errorLogin() {
        return new ResponseInfo().failed("登录失效，请重新登录");
    }

    @GetMapping("/404")
    public ResponseInfo notFound() {
        return new ResponseInfo().failed("404.......");
    }
}
