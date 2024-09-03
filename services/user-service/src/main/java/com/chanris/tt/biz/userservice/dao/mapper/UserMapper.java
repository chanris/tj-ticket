package com.chanris.tt.biz.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanris.tt.biz.userservice.dao.entity.UserDO;
import com.chanris.tt.biz.userservice.dao.entity.UserMailDO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description
 */
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 注销用户
     *
     * @param userMailDO 注销用户入参
     */
    void deletionUser(UserDO userMailDO);
}
