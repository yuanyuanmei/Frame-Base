package com.ljcx.code.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljcx.user.vo.MemberVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 房间号
     */
    private Integer roomId;

    private String label;

    /**
     * 上级id
     */
    private Long pid;

    /**
     * 路径
     */
    private String path;

    /**
     * 上级名称
     */
    private String pname;

    /**
     * 上级路径
     */
    private String parentPath;

    //子列表
    private List<TeamInfoVo> children;

    /**
     * 无人机设备
     */
    private List<UavInfoVo> equipUavList;

    /**
     * 无人车设备
     */
    private List<CarInfoVo> equipCarList;

    /**
     * 人员
     */
    private List<MemberVo> equipMemberList;


    public String getLabel(){
        return name;
    }

}
