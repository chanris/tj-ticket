package com.chanris.tt.biz.userservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注销请求参数
 */
@Data
public class UserDeletionReqDTO {

    /**
     * 用户名
     */
    private String username;
}
