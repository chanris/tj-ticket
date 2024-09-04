package com.chanris.tt.biz.userservice.service;

import com.chanris.tt.biz.userservice.dto.req.UserDeletionReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserLoginReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserRegisterReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserLoginRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserRegisterRespDTO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户登录接口
 */
public interface UserLoginService {

    /**
     * 用户登录接口
     *
     * @param requestParam 用户登录入参
     * @return 用户登录返回结果
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 通过 Token 检查用户是否登录
     *
     * @param accessToken 用户登录 Token 凭证
     * @return 用户是否登录返回结果
     */
    UserLoginRespDTO checkLogin(String accessToken);

    /**
     * 用户退出登录
     *
     * @param username 用户登录 Token 凭证
     */
    void logout(String accessToken);

    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return 用户名是否存在返回结果
     */
    boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param requestParam 用户注册入参
     * @return 用户注册返回结果
     */
    UserRegisterRespDTO register(UserRegisterReqDTO requestParam);

    /**
     * 注销用户
     *
     * @param requestParam
     */
    void deletion(UserDeletionReqDTO requestParam);
}
