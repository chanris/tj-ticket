package com.chanris.tt.biz.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanris.tt.biz.userservice.dao.entity.UserPhoneDO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户手机号持久层
 */
public interface UserPhoneMapper extends BaseMapper<UserMapper> {
    /**
     * 注销用户
     *
     * @param userPhoneDO 注销用户入参
     */
    void deletionUser(UserPhoneDO userPhoneDO);
}
