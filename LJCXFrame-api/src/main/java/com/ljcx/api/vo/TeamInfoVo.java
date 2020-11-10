package com.ljcx.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.api.beans.CarInfoBean;
import com.ljcx.api.beans.UavInfoBean;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.vo.MemberVo;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class TeamInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String userName;

    private String memo;

    private Integer roomId;

    //无人机列表
    private Set<UavInfoVo> uavs;

    //指挥车列表
    private Set<CarInfoVo> cars;

    //人员列表
    private Set<MemberVo> members;

    //无人机数量
    private int uavNums;

    //指挥车数量
    private int carNums;

    //人员数量
    private int memberNums;

    //无人机在线数量
    private long uavOnlineNums;

    //指挥车在线数量
    private long carOnlineNums;

    //人员在线数量
    private long memberOnlineNums;

    //在线率
    private String onlineRate;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private String imGroupId;

    private String imGroupName;

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

    public int getUavNums() {
        return uavs.size();
    }

    public int getCarNums() {
        return cars.size();
    }

    public int getMemberNums() {
        return members.size();
    }
}
