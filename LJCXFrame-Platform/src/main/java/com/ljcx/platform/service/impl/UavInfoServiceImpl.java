package com.ljcx.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.constant.RedisConstant;
import com.ljcx.common.utils.DateUtils;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.platform.dao.TaskRecordsDao;
import com.ljcx.platform.dao.UavInfoDao;
import com.ljcx.platform.dto.StatusDto;
import com.ljcx.platform.dto.UavInfoDto;
import com.ljcx.platform.mgbean.UavInfoMongo;
import com.ljcx.platform.mgdao.UavInfoMongoDbDao;
import com.ljcx.platform.service.TeamInfoService;
import com.ljcx.platform.service.UavInfoService;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.platform.shiro.util.UserUtil;
import com.ljcx.platform.vo.MapVo;
import com.ljcx.platform.vo.TeamInfoVo;
import com.ljcx.platform.vo.UavInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("uavInfoService")
@Slf4j
public class UavInfoServiceImpl extends ServiceImpl<UavInfoDao, UavInfoBean> implements UavInfoService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private UavInfoDao uavInfoDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private TaskRecordsDao taskRecordsDao;

    @Autowired
    private UavInfoMongoDbDao uavInfoMongoDbDao;

    @Override
    public IPage<UavInfoBean> pageList(UavInfoDto uavInfoDto) {
        IPage<UavInfoBean> page = new Page<>();
        page.setCurrent(uavInfoDto.getPageNum());
        page.setSize(uavInfoDto.getPageSize());
        return uavInfoDao.pageList(page,uavInfoDto);
    }

    @Override
    public ResponseInfo getState(Long teamId) {
        Long nums = 0l;
        Long totalNums = 0l;
        long currentTime = System.currentTimeMillis();
//        if(teamId != -1){
            totalNums = Long.valueOf(uavInfoDao.listByTeamId(teamId).size());
            nums = redisTemplate.opsForZSet().count(RedisConstant.UAV_ONLINE_NUMS_+teamId,currentTime-5*60*1000,currentTime);
//        }else{
//            totalNums = Long.valueOf(uavInfoDao.listByUserId(UserUtil.getCurrentUser().getId()).size());
//            List<TeamInfoVo> list = teamInfoService.list(UserUtil.getCurrentUser().getId());
//            for(TeamInfoVo item :list){
//                nums += redisTemplate.opsForZSet().count(RedisConstant.UAV_ONLINE_NUMS_+item.getId(),currentTime-5*60*1000,currentTime);
//            }
//
//        }

        MapVo map1 = new MapVo("离线",totalNums - nums);
        MapVo map2 = new MapVo("作业",nums);
        MapVo map3 = new MapVo("异常",0l);
        return new ResponseInfo(Arrays.asList(map1,map2,map3));
    }

    @Override
    public UavInfoVo planPanel(UavInfoDto uavInfoDto) {
        String s = redisTemplate.opsForValue().get(RedisConstant.UAV_STATE_+uavInfoDto.getTeamId()+"_" + uavInfoDto.getId());
        Map<String, Number> taskMap = taskRecordsDao.dataByPlanId(uavInfoDto.getId());
        UavInfoVo redisUav = JSONObject.parseObject(s, UavInfoVo.class);
        if(redisUav != null){
            redisUav.setTaskNum(taskMap.get("task_num").longValue());
            redisUav.setTaskHours(taskMap.get("flightSumSeconds").longValue());
            redisUav.setFlyTime(DateUtils.secondToTimeByPanel(taskMap.get("flightSumSeconds").longValue()));
        }
        return redisUav;
    }

    @Override
    public ResponseInfo saveRoutes(UavInfoVo uavInfoVo) {
        UavInfoMongo bean = generator.convert(uavInfoVo, UavInfoMongo.class);
        bean.setUavId(uavInfoVo.getId());
        uavInfoMongoDbDao.save(bean);
        return new ResponseInfo().success("添加成功");
    }

    @Override
    public ResponseInfo findRoutes(UavInfoMongo uavInfoMongo) {
        List<UavInfoMongo> uavInfoMongos = uavInfoMongoDbDao.queryList(uavInfoMongo);
        return new ResponseInfo(uavInfoMongos);
    }


}
