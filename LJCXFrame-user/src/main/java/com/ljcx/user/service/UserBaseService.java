package com.ljcx.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.user.beans.UserBaseBean;
import com.ljcx.user.dto.AccountDto;
import com.ljcx.user.dto.UserDto;
import com.ljcx.user.vo.MemberVo;
import com.ljcx.user.vo.UserVo;

import java.util.List;

/**
 * 用户基础业务类
 */
public interface UserBaseService extends IService<UserBaseBean> {

    IPage<UserBaseBean> pageList(UserDto userDto);

    ResponseInfo updateByDto(UserDto userDto);

    ResponseInfo updateStatus(UserDto userDto);

    UserVo info(Long userId);

    List<MemberVo> listByTeamId(Long teamId);

    List<MemberVo> listUpTeamByTeamId(Long teamId);

    int deleteImAccount(List<String> usernames);

    ResponseInfo del(Long id);
}
