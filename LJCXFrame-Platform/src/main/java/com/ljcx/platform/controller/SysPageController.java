package com.ljcx.platform.controller;

import com.ljcx.common.utils.http.CookieUtils;
import com.ljcx.platform.shiro.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;

/**
 * 系统页面视图
 */
@Controller
public class SysPageController{

    @GetMapping("modules/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "modules/" + module + "/" + url;
    }

    @GetMapping(value = {"/", "index.html"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = {"task_detail.html/{id}"})
    public String taskDetail(@PathVariable Long id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("user", UserUtil.getCurrentUser());
        return "task_detail";
    }

    @GetMapping(value = {"task_list.html"})
    public String taskList(Model model) {
        model.addAttribute("user", UserUtil.getCurrentUser());
        return "task_list";
    }

    @GetMapping(value = {"home.html"})
    public String home() {
        return "home";
    }

    @GetMapping("/login/errorLogin")
    public String errorLogin() {
        return "login";
    }

    @GetMapping({"/login.html","logout"})
    public String login(Model model) {
        Cookie remember_username = CookieUtils.getCookie("REMEMBER_USERNAME");
        if(remember_username != null){
            model.addAttribute("username",remember_username.getValue());
        }
        return "login";
    }

    @GetMapping("404")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("500")
    public String wrong() {
        return "error/500";
    }

    @GetMapping(value = {"websocket_demo.html"})
    public String test() {
        return "websocket_demo";
    }

}
