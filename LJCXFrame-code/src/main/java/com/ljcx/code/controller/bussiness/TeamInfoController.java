package com.ljcx.code.controller.bussiness;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.code.dto.TeamInfoDto;
import com.ljcx.code.service.TeamInfoService;
import com.ljcx.code.vo.TeamInfoVo;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.framework.sys.service.IGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 团队controller
 */
@Api(value = "团队模块")
@RestController
@RequestMapping("/team/list")
@Slf4j
public class TeamInfoController {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TeamInfoService teamInfoService;

    /**
     * 分页查询列表
     * @param info
     * info(teamInfoDto 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("team:list:query")
    public ResponseInfo pageList(@RequestBody TeamInfoDto teamInfoDto) {
        return new ResponseInfo(teamInfoService.pageList(teamInfoDto));
    }

    @PostMapping("/save")
    @RequiresPermissions("team:list:add")
    public ResponseInfo save(@RequestBody String info){
        TeamInfoDto teamInfoDto = JSONObject.parseObject(info, TeamInfoDto.class);
        return teamInfoService.saveTeamInfo(teamInfoDto);
    }

    @PostMapping("/del")
    @RequiresPermissions("team:list:del")
    public ResponseInfo del(@RequestBody String info){
        TeamInfoDto teamInfoDto = JSONObject.parseObject(info, TeamInfoDto.class);
        return teamInfoService.delTeam(teamInfoDto);
    }
    /**
     * 团队树结构
     * @return
     */
    @PostMapping("/treeList")
    @RequiresPermissions("team:list:query")
    public ResponseInfo treeList(@RequestBody TeamInfoDto teamInfoDto) {
        List<TeamInfoVo> parentList = teamInfoService.treeList(teamInfoDto);
        if(teamInfoDto.getId() != null && teamInfoDto.getId() == 0){
            TeamInfoVo parent = new TeamInfoVo();
            parent.setName("所有团队");
            parent.setId(0l);
            parent.setChildren(parentList);
            return new ResponseInfo(Arrays.asList(parent));
        }
        return new ResponseInfo(parentList);
    }

    /**
     * 下拉树结构
     * @return
     */
    @PostMapping("/selectTree")
    @RequiresPermissions("team:list:query")
    public ResponseInfo selectTree() {
        return new ResponseInfo(teamInfoService.treeList(null));
    }

    /**
     * 团队绑定成员
     * @return
     */
    @PostMapping("/bindTeam")
    @RequiresPermissions("team:list:add")
    @ValidateCustom(TeamInfoDto.class)
    public ResponseInfo bindTeam(@RequestBody String info) {
        TeamInfoDto teamInfoDto = JSONObject.parseObject(info, TeamInfoDto.class);
        return new ResponseInfo(teamInfoService.bindTeam(teamInfoDto));
    }

    /**
     * 解除绑定成员
     * @return
     */
    @PostMapping("/cancelTeam")
    @RequiresPermissions("team:list:del")
    @ValidateCustom(TeamInfoDto.class)
    public ResponseInfo cancelTeam(@RequestBody String info) {
        TeamInfoDto teamInfoDto = JSONObject.parseObject(info, TeamInfoDto.class);
        return new ResponseInfo(teamInfoService.cancelTeam(teamInfoDto));
    }

    /**
     * 获取团队集合
     */
    @PostMapping("/list")
    @RequiresPermissions("team:list:query")
    public ResponseInfo list(@RequestBody TeamInfoDto teamInfoDto) {
        return new ResponseInfo(teamInfoService.list(teamInfoDto));
    }

    /**
     * 获取团队设备信息
     */
    @PostMapping("/equipmentList")
    @RequiresPermissions("team:list:query")
    public ResponseInfo equipmentList(@RequestBody TeamInfoDto teamInfoDto) {
        if(teamInfoDto != null && teamInfoDto.getId() != null){
            return new ResponseInfo(teamInfoService.equipmentList(teamInfoDto.getId()));
        }
        return new ResponseInfo().success("id不能为空");
    }



}
