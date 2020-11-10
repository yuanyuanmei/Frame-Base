package com.ljcx.platform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.platform.beans.CarInfoBean;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.vo.MemberVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class TeamInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String nickname;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private Long userId;

    private String memo;

    private String imGroupId;

    private String imGroupName;

    //无人机列表
    private Set<UavInfoVo> uavs;

    //无人机列表
    private Set<UavInfoVo> online_uavs;

    //无人机列表
    private Set<UavInfoVo> offline_uavs;

    //指挥车列表
    private Set<CarInfoVo> cars;

    //指挥车列表
    private Set<CarInfoVo> online_cars;

    //指挥车列表
    private Set<CarInfoVo> offline_cars;

    //成员列表
    private Set<MemberVo> members;

    //成员列表
    private Set<MemberVo> online_members;

    //成员列表
    private Set<MemberVo> offline_members;

    /**
     * 上级id
     */
    private Long pId;

    /**
     * 路径
     */
    private String path;

    //子列表
    private List<TeamInfoVo> children;

    //树节点是否打开
    private boolean open = true;

    //有没有权限点击
    private boolean canSelect;
}
