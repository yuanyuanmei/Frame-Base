package com.ljcx.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.platform.beans.TeamInfoBean;
import com.ljcx.platform.dao.TeamInfoDao;
import com.ljcx.platform.dto.TeamInfoDto;
import com.ljcx.platform.service.TeamInfoService;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.CarInfoVo;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TeamInfoVo;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.vo.UavInfoVo;
import com.ljcx.user.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 团队信息类
 */
@Service
@Slf4j
public class TeamInfoServiceImpl extends ServiceImpl<TeamInfoDao, TeamInfoBean> implements TeamInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private TeamInfoDao teamInfoDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public List<TeamInfoVo> treeList(Long userId) {
        TeamInfoDto teamInfoDto = new TeamInfoDto();
        teamInfoDto.setUserId(userId);
        List<TeamInfoVo> allList = teamInfoDao.teamList(teamInfoDto);
        List<TeamInfoVo> parentList = allList.stream().filter(item -> item.getPId() == 0).collect(Collectors.toList());
        parentList.stream().forEach(item -> {
            item.setChildren(childrenList(item.getId(),allList));
        });
        return parentList;
    }

    @Override
    public List<TeamInfoVo> list(Long userId) {
        TeamInfoDto teamInfoDto = new TeamInfoDto();
        teamInfoDto.setUserId(userId);
        List<TeamInfoVo> allList = teamInfoDao.teamList(teamInfoDto);
        return allList;
    }

    private List<TeamInfoVo> childrenList(Long parentId, List<TeamInfoVo> teamInfoVoList) {
        List<TeamInfoVo> children = new ArrayList<>();
        teamInfoVoList.stream().forEach(item->{
            if(item.getPId() == parentId){
                item.setChildren(childrenList(item.getId(),teamInfoVoList));
                children.add(item);
            }
        });
        return children;
    }

    @Override
    public TeamInfoVo info(Long id) {
        TeamInfoVo info;
        if(id == -1){
            info = new TeamInfoVo();
            info.setName("所有团队");
            List<TeamInfoVo> list = list(UserUtil.getCurrentUser().getId());
            Set<MemberVo> redisMemberList = new HashSet<>();
            Set<UavInfoVo> redisUavList = new HashSet<>();
            Set<CarInfoVo> redisCarList = new HashSet<>();
            for(TeamInfoVo item: list){
                TeamInfoVo infoVo = teamInfoDao.info(item.getId());
                redisMemberList.addAll(dynamicMemberList(infoVo.getMembers(),UserUtil.getCurrentUser().getId(),item.getId()));
                redisUavList.addAll(dynamicUavList(infoVo.getUavs(),item.getId()));
                redisCarList.addAll(dynamicCarList(infoVo.getCars(),item.getId()));
            }
            info.setUavs(redisUavList);
            info.setMembers(redisMemberList);
            info.setCars(redisCarList);
        }else{
            info = teamInfoDao.info(id);
//            info.setUavs(dynamicUavList(info.getUavs()));
//            info.setMembers(dynamicMemberList(info.getMembers()));
//            info.setCars(dynamicCarList(info.getCars()));


            Set<UavInfoVo> online_uavs = dynamicUavList(info.getUavs(),id).stream().filter(item -> item.getStatus() == 1).collect(Collectors.toSet());
            info.setOnline_uavs(online_uavs);


            Set<UavInfoVo> offline_uavs = dynamicUavList(info.getUavs(),id).stream().filter(item->item.getStatus() == 0).collect(Collectors.toSet());
            info.setOffline_uavs(offline_uavs);

            Set<CarInfoVo> online_cars = dynamicCarList(info.getCars(),id).stream().filter(item->item.getStatus() == 1).collect(Collectors.toSet());
            info.setOnline_cars(online_cars);

            Set<CarInfoVo> offline_cars = dynamicCarList(info.getCars(),id).stream().filter(item->item.getStatus() == 0).collect(Collectors.toSet());
            info.setOffline_cars(offline_cars);

            Set<MemberVo> online_members = dynamicMemberList(info.getMembers(),UserUtil.getCurrentUser().getId(),id).stream().filter(item->item.getStatus() == 1).collect(Collectors.toSet());
            info.setOnline_members(online_members);

            Set<MemberVo> offline_members = dynamicMemberList(info.getMembers(),UserUtil.getCurrentUser().getId(),id).stream().filter(item->item.getStatus() == 0).collect(Collectors.toSet());
            info.setOffline_members(offline_members);

        }
        return info;
    }

    private Set<MemberVo> dynamicMemberList(Set<MemberVo> list,Long userId,Long teamId) {
        Set<MemberVo> redisMemberList = new HashSet<>();
        for (MemberVo item : list){
            if(item.getId() == userId){
                continue;
            }
            if(redisTemplate.hasKey(RedisConstant.MEMBER_STATE_+teamId+"_"+item.getId())){
                String s = redisTemplate.opsForValue().get(RedisConstant.MEMBER_STATE_+teamId+"_" + item.getId());
                MemberVo redisMember = JSONObject.parseObject(s, MemberVo.class);
                redisMember.setNickname(item.getNickname());
                redisMember.setName(item.getNickname());
                redisMember.setUsername(item.getUsername());
                redisMember.setCreateTime(item.getCreateTime());
                redisMember.setPhone(item.getPhone());
                redisMember.setHeaderUrl(item.getHeaderUrl());
                redisMember.setRoleName(item.getRoleName());
                redisMemberList.add(redisMember);
            }else{
                redisMemberList.add(item);
            }
        }
        return redisMemberList;
    }

    /**
     * 动态飞机数据
     * @param list
     * @return
     */
    private Set<UavInfoVo> dynamicUavList(Set<UavInfoVo> list,Long teamId){
        Set<UavInfoVo> redisUavList = new HashSet<>();
        for (UavInfoVo uav : list){
            if(redisTemplate.hasKey(RedisConstant.UAV_STATE_+teamId+"_"+uav.getId())){
                String s = redisTemplate.opsForValue().get(RedisConstant.UAV_STATE_+teamId+"_" + uav.getId());
                UavInfoVo redisUav = JSONObject.parseObject(s, UavInfoVo.class);
                redisUav.setName(uav.getName());
                redisUav.setModel(uav.getModel());
                redisUav.setNo(uav.getNo());
                redisUav.setId(uav.getId());
                //添加飞机直播状态
                if(redisTemplate.hasKey(RedisConstant.UAV_LIVE_STATE_+uav.getId())){
                    String live = redisTemplate.opsForValue().get(RedisConstant.UAV_LIVE_STATE_ + uav.getId());
                    JSONObject liveDto = JSONObject.parseObject(live);
                    redisUav.setAction(liveDto.getString("action"));
                    redisUav.setFlyPlayAddress(liveDto.getString("flyPlayAddress"));
                    redisUav.setHlsPlayAddress(liveDto.getString("hlsPlayAddress"));
                    redisUav.setRtmpPlayAddress(liveDto.getString("rtmpPlayAddress"));
                }else{
                    redisUav.setAction("close");
                }
                redisUavList.add(redisUav);
            }else{
                redisUavList.add(uav);
            }
        }
        return redisUavList;
    }

    /**
     * 动态指挥车数据
     * @param list
     * @return
     */
    private Set<CarInfoVo> dynamicCarList(Set<CarInfoVo> list,Long teamId){
        Set<CarInfoVo> redisCarList = new HashSet<>();
        for (CarInfoVo item : list){
            if(redisTemplate.hasKey(RedisConstant.CAR_STATE_+teamId+"_"+item.getId())){
                String s = redisTemplate.opsForValue().get(RedisConstant.CAR_STATE_+teamId+"_" + item.getId());
                CarInfoVo redisCar = JSONObject.parseObject(s, CarInfoVo.class);
                redisCar.setName(item.getName());
                redisCarList.add(redisCar);
            }else{
                redisCarList.add(item);
            }
        }
        return redisCarList;
    }

    @Override
    public List<MapVo> getDataNums(Long teamId) {
        List<MapVo> dataNums = teamInfoDao.getDataNums(teamId, UserUtil.getCurrentUser().getId());
        return dataNums;
    }

    @Override
    public int getTotalNums(Long teamId) {
        return teamInfoDao.getTotalNums(teamId,UserUtil.getCurrentUser().getId());
    }

}

