package com.ljcx.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.code.beans.TeamInfoBean;
import com.ljcx.code.dao.CarInfoDao;
import com.ljcx.code.dao.UavInfoDao;
import com.ljcx.code.dto.TeamInfoDto;
import com.ljcx.code.shiro.util.UserUtil;
import com.ljcx.code.vo.CarInfoVo;
import com.ljcx.code.vo.EqumentCountVo;
import com.ljcx.code.vo.TeamInfoVo;
import com.ljcx.code.dao.TeamInfoDao;
import com.ljcx.code.service.TeamInfoService;
import com.ljcx.common.base.BaseTree;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.framework.im.dto.IMGroupDto;
import com.ljcx.framework.im.dto.IMGroupMemberDto;
import com.ljcx.framework.im.resp.IMMember;
import com.ljcx.framework.im.resp.IMResponse;
import com.ljcx.framework.im.service.IMGroupService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dao.UserBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 团队信息类
 */
@Service
public class TeamInfoServiceImpl extends ServiceImpl<TeamInfoDao, TeamInfoBean> implements TeamInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TeamInfoDao teamInfoDao;

    @Autowired
    private UserBaseDao userBaseDao;

    @Autowired
    private UavInfoDao uavInfoDao;

    @Autowired
    private CarInfoDao carInfoDao;

    @Autowired
    private IMGroupService groupService;


    @Override
    public IPage<TeamInfoVo> pageList(TeamInfoDto teamInfoDto) {
        IPage<TeamInfoVo> page = new Page<>();
        page.setCurrent(teamInfoDto.getPageNum());
        page.setSize(teamInfoDto.getPageSize());
        return teamInfoDao.pageList(page,teamInfoDto, UserUtil.getCurrentUser());
    }

    @Override
    public List<TeamInfoVo> list(TeamInfoDto teamInfoDto) {
        return teamInfoDao.pageList(teamInfoDto, UserUtil.getCurrentUser());
    }

    @Override
    @Transactional
    public ResponseInfo saveTeamInfo(TeamInfoDto teamInfoDto) {
        TeamInfoBean bean = generator.convert(teamInfoDto, TeamInfoBean.class);
        QueryWrapper searchWrapper = new QueryWrapper();
        searchWrapper.eq("name",bean.getName());
        List<TeamInfoBean> list = teamInfoDao.selectList(searchWrapper);
        if(bean.getId() == null){
            if(list.size() > 0){
                return new ResponseInfo().failed("团队名称不能重复，请重新设置");
            }
            bean.setRoomId(getRoomId());
            teamInfoDao.insert(bean);
            //将路径修改
            bean.setPath(teamInfoDto.getPrePath()+"/"+bean.getId());
            teamInfoDao.updateById(bean);
            //将用户管理员新增进团队中
            TeamInfoDto bindDto = new TeamInfoDto();
            bindDto.setId(bean.getId());
            bindDto.setMIds(Arrays.asList(teamInfoDto.getUserId()));
            bindDto.setMType(3);
            bindTeam(bindDto);
        }else{
            if(list.size() > 0 && list.get(0).getId() != bean.getId()){
                return new ResponseInfo().failed("团队名称不能重复，请重新设置");
            }
            teamInfoDao.updateById(bean);
        }

        UserBaseBean userBaseBean = userBaseDao.selectById(bean.getUserId());
        IMGroupDto imGroupDto = new IMGroupDto();
        imGroupDto.setGroupId("Group_"+bean.getId());
        imGroupDto.setName("Group_"+bean.getId());
        imGroupDto.setOwner_Account(userBaseBean.getUsername());
        IMResponse imResponse = groupService.groupCreate(imGroupDto);
        if(imResponse.getActionStatus().equals("OK") || imResponse.getErrorCode() == 10021){
            bean.setImGroupId(imGroupDto.getGroupId());
            bean.setImGroupName(bean.getName()+"的群聊");
            teamInfoDao.updateById(bean);
        }

        //更改用户为成员

        return new ResponseInfo().success("保存成功");
    }

    private int getRoomId(){
        Random random = new Random();
        int roomId = random.nextInt(10000);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_id",roomId);
        List list = teamInfoDao.selectList(wrapper);
        if(list.size() > 0){
            getRoomId();
        }
        return roomId;
    }

    @Override
    public List<TeamInfoVo> treeList(TeamInfoDto teamInfoDto) {
        List<TeamInfoVo> allList = teamInfoDao.pageList(teamInfoDto, UserUtil.getCurrentUser());
        List<TeamInfoVo> parentList = allList.stream().filter(item -> item.getPid() == 0).collect(Collectors.toList());
        parentList.stream().forEach(item -> {
            item.setChildren(childrenList(item.getId(),allList));
        });
        return parentList;
    }

    private List<TeamInfoVo> childrenList(Long parentId, List<TeamInfoVo> teamInfoVoList) {
        List<TeamInfoVo> children = new ArrayList<>();
        teamInfoVoList.stream().forEach(item->{
            if(item.getPid() == parentId){
                item.setChildren(childrenList(item.getId(),teamInfoVoList));
                children.add(item);
            }
        });
        return children;
    }

    @Override
    @Transactional
    public ResponseInfo bindTeam(TeamInfoDto teamInfoDto) {
        if(teamInfoDto.getUavIds() != null && teamInfoDto.getUavIds().size() > 0){
            TeamInfoDto uavParam = new TeamInfoDto();
            uavParam.setId(teamInfoDto.getId());
            uavParam.setMType(1);
            uavParam.setMIds(teamInfoDto.getUavIds());

            teamInfoDao.bindTeam(uavParam);
        }

        if(teamInfoDto.getCarIds() != null && teamInfoDto.getCarIds().size() > 0){
            TeamInfoDto carParam = new TeamInfoDto();
            carParam.setId(teamInfoDto.getId());
            carParam.setMType(2);
            carParam.setMIds(teamInfoDto.getCarIds());
            teamInfoDao.bindTeam(carParam);
        }

        if(teamInfoDto.getMemberIds() != null && teamInfoDto.getMemberIds().size() > 0){
            TeamInfoDto memberParam = new TeamInfoDto();
            memberParam.setId(teamInfoDto.getId());
            memberParam.setMType(3);
            memberParam.setMIds(teamInfoDto.getMemberIds());
            teamInfoDao.bindTeam(memberParam);
            //添加账号进群组
            IMGroupMemberDto imGroupMemberDto = getMemberList(memberParam,"add");
            if(imGroupMemberDto != null){
                groupService.addMember(imGroupMemberDto);
            }
        }
        return new ResponseInfo().success("绑定成功");
    }

    @Override
    @Transactional
    public ResponseInfo cancelTeam(TeamInfoDto teamInfoDto) {
        teamInfoDao.cancelTeam(teamInfoDto);
        IMGroupMemberDto imGroupMemberDto = getMemberList(teamInfoDto,"del");
        if(imGroupMemberDto != null){
            groupService.delMember(imGroupMemberDto);
        }
        return new ResponseInfo().success("解除成功");
    }

    @Override
    public TeamInfoVo equipmentList(Long id) {
        TeamInfoVo item = generator.convert(teamInfoDao.selectById(id), TeamInfoVo.class);
        item.setEquipUavList(uavInfoDao.equipUavList());
        item.setEquipCarList(carInfoDao.equipCarList());
        item.setEquipMemberList(userBaseDao.equipMemberList(id));
        return item;
    }

    public List<Long> userTeamList(){
        List<Long> teamIds = teamInfoDao.teamIdsByUserId(UserUtil.getCurrentUser().getId());
        Set<Long> teamIdSet = new HashSet<>(teamIds);
        for(Long teamId: teamIds){
            List<Long> downList = teamInfoDao.teamDownList(teamId);
            teamIdSet.addAll(downList);
        }
        return new ArrayList<>(teamIdSet);
    }

    @Override
    public int delRelationShipByMid(Long mId, int mType) {
        return 0;
    }

    @Override
    public ResponseInfo delTeam(TeamInfoDto teamInfoDto) {
        if(teamInfoDao.teamEqumentCount(teamInfoDto.getId()) == 0){
            teamInfoDao.deleteById(teamInfoDto.getId());
            return new ResponseInfo().success("删除成功");
        }
        return new ResponseInfo().success("团队下还有成员，不可删除");
    }

    @Override
    public List<EqumentCountVo> equipmentCount(List<Long> teamIds) {
        if(teamIds != null && teamIds.size() > 0){
            return teamInfoDao.equmentCount(teamIds);
        }
        return new ArrayList<>();
    }

    private IMGroupMemberDto getMemberList(TeamInfoDto teamInfoDto,String opt){
        TeamInfoBean teamInfoBean = teamInfoDao.selectById(teamInfoDto.getId());
        //添加账号进群组
        if(teamInfoDto.getMType() == 3 && StringUtils.isNotEmpty(teamInfoBean.getImGroupId())){
            List<UserBaseBean> userBaseBeans = userBaseDao.selectBatchIds(teamInfoDto.getMIds());
            IMGroupMemberDto imGroupMemberDto = new IMGroupMemberDto();
            if(opt.equals("add")){
                List<IMMember> MemberList = userBaseBeans.stream().map(user->{return new IMMember(user.getUsername());}).collect(Collectors.toList());
                imGroupMemberDto.setMemberList(MemberList);
            }else{
                List<String> DelAccountList = userBaseBeans.stream().map(user->{return user.getUsername();}).collect(Collectors.toList());
                imGroupMemberDto.setMemberToDel_Account(DelAccountList);
            }
            imGroupMemberDto.setGroupId(teamInfoBean.getImGroupId());


            return imGroupMemberDto;
        }
        return null;
    }
}

