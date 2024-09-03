package com.chanris.tt.biz.userservice.service.impl;

import com.chanris.tt.biz.userservice.dto.req.UserUpdateReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserQueryRespDTO;
import com.chanris.tt.biz.userservice.service.UserService;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户信息接口实现层
 */
public class UserServiceImpl implements UserService {



    @Override
    public UserQueryRespDTO queryUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserQueryRespDTO queryUserByUsername(String username) {
        return null;
    }

    @Override
    public UserQueryRespDTO queryActualUserByUsername(String username) {
        return null;
    }

    @Override
    public Integer queryUserDeletionNum(Integer idType, String idCard) {
        return null;
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {

    }
}
