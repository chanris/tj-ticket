package com.chanris.tt.biz.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanris.tt.biz.userservice.dao.entity.UserDeletionDO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注销表持久层
 */
public interface UserDeletionMapper extends BaseMapper<UserDeletionDO> { // 继承 mybatis-plus的BaseMapper获得CURD能力
}
