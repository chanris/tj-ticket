package com.chanris.tt.biz.userservice.service;

import com.chanris.tt.biz.userservice.dto.req.UserUpdateReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserQueryRespDTO;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户信息接口层
 */

public interface UserService {

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param userId
     * @return
     */
    UserQueryRespDTO queryUserByUserId(@NotEmpty String userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    UserQueryRespDTO queryUserByUsername(@NotEmpty String username);

    /**
     * 根据用户查询用户无脱敏信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    UserQueryRespDTO queryActualUserByUsername(@NotEmpty String username);


    /**
     * 根据证件类型和证件号查询注销次数
     *
     * @param idType 证件类型
     * @param idCard 证件号
     * @return 注销次数
     */
    Integer queryUserDeletionNum(Integer idType, String idCard);

    /**
     * 根据用户 ID 修改用户信息
     *
     * @param requestParam 用户信息入参
     */
    void update(UserUpdateReqDTO requestParam);
}
