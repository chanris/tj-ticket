package com.chanris.tt.biz.userservice.service.impl;

import com.chanris.tt.biz.userservice.dao.mapper.*;
import com.chanris.tt.biz.userservice.dto.req.UserDeletionReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserLoginReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserRegisterReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserLoginRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserRegisterRespDTO;
import com.chanris.tt.biz.userservice.service.UserLoginService;
import com.chanris.tt.biz.userservice.service.UserService;
import com.chanris.tt.framework.starter.DistributedCache;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户登录接口实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserReuseMapper userReuseMapper;
    private final UserDeletionMapper userDeletionMapper;
    private final UserPhoneMapper userPhoneMapper;
    private final UserMailMapper userMailMapper;
    private final RedissonClient redissonClient;
    private final DistributedCache distributedCache;
    private final AbstractChainContext<UserRegisterReqDTO> abstractChainContext;
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        return null;
    }

    @Override
    public UserLoginReqDTO checkLogin(String accessToken) {
        return null;
    }

    @Override
    public void logout(String accessToken) {

    }

    @Override
    public boolean hasUsername(String username) {
        return false;
    }

    @Override
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        return null;
    }

    @Override
    public void deletion(UserDeletionReqDTO requestParam) {

    }
}
