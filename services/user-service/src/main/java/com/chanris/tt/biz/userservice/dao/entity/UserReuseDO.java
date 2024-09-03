package com.chanris.tt.biz.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chanris.tt.framework.starter.database.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户名复用实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_reuse")
public class UserReuseDO extends BaseDO {
    /**
     * 用户名
     */
    private String username;
}
