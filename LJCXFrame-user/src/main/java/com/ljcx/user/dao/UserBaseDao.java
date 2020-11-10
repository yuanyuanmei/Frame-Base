package com.ljcx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dto.UserDto;
import com.ljcx.user.vo.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserBaseDao extends BaseMapper<UserBaseBean> {

    /**
     * 用户分页列表
     * @param page
     * @param userDto
     * @return
     */
    IPage<UserBaseBean> pageList(IPage<UserBaseBean> page,@Param("item") UserDto userDto);

    int updateStatus(UserDto userDto);

    List<MemberVo> listByTeamId(@Param("teamId") Long teamId);

    List<MemberVo> listUpTeamByTeamId(@Param("teamId") Long teamId);

    int deleteImAccount(@Param("usernames") List<String> usernames);

    List<MemberVo> equipMemberList(@Param("teamId") Long teamId);

}