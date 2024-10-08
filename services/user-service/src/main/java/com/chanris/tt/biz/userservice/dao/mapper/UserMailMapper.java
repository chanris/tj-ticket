package com.chanris.tt.biz.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanris.tt.biz.userservice.dao.entity.UserMailDO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/3
 * @description 用户邮箱持久层
 */
public interface UserMailMapper extends BaseMapper<UserMailDO> {
    /**
     * 注销用户
     *
     * @param userMailDO 注销用户入参
     */
    void deletionUser(UserMailDO userMailDO);
}
