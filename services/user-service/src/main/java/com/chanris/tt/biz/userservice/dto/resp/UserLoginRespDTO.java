package com.chanris.tt.biz.userservice.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户登录返回参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDTO {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * Token
     */
    private String accessToken;
}
