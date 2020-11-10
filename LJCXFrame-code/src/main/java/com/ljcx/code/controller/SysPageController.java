package com.ljcx.code.controller;

import com.ljcx.code.controller.user.PermissionController;
import com.ljcx.code.service.TaskService;
import com.ljcx.code.service.TeamInfoService;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.code.vo.EqumentCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统页面视图
 */
@Controller
@Slf4j
public class SysPageController{

    @Autowired
    private PermissionController permsController;

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private TaskService taskService;

    @GetMapping("modules/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "modules/" + module + "/" + url;
    }

    @GetMapping(value = {"/", "index.html"})
    public String index(Model model) {
        model.addAttribute("user", UserUtil.getCurrentUser());
        model.addAttribute("perms",permsController.firstLevel());
        return "index";
    }

    @GetMapping(value = {"home.html"})
    public String home(Model model) {
        model.addAttribute("user", UserUtil.getCurrentUser());
        List<Long> teamIds = teamInfoService.userTeamList();
        model.addAttribute("teamCount", teamIds.size());
        List<EqumentCountVo> equmentCountVos = teamInfoService.equipmentCount(teamIds);
        Map<Integer, List<EqumentCountVo>> equmentCountMap = equmentCountVos.stream().collect(Collectors.groupingBy(EqumentCountVo::getType));
        model.addAttribute("equmentCountMap", equmentCountMap);
        model.addAttribute("taskCount", taskService.TaskCount(teamIds));
        return "home";
    }

    @GetMapping("/login/errorLogin")
    public String errorLogin() {
        return "login";
    }
    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("404")
    public String notFound() {
        return "404";
    }

    @GetMapping(value = {"websocket_demo.html"})
    public String test() {
        return "websocket_demo";
    }

}
