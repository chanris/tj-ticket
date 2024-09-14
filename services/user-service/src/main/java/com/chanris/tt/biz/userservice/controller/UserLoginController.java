package com.chanris.tt.biz.userservice.controller;

import com.chanris.tt.biz.userservice.dto.req.UserLoginReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserLoginRespDTO;
import com.chanris.tt.biz.userservice.service.UserLoginService;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户登录控制层
 */
@RestController
@RequiredArgsConstructor
public class UserLoginController {
    private final UserLoginService userLoginService;

    /**
     * 用户登录
     */
    @PostMapping("/api/user-service/v1/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        return Results.success(userLoginService.login(requestParam));
    }

    /**
     * 通过 Token 检查用户是否登录
     */
    @GetMapping("/api/user-service/check-login")
    public Result<UserLoginRespDTO> checkLogin(@RequestParam("accessToken") String accessToken) {
        UserLoginRespDTO result = userLoginService.checkLogin(accessToken);
        return Results.success(result);
    }

    /**
     * 用户退出登录
     *
     * @param accessToken 用户登录 token 凭证
     */
    @GetMapping("/api/user-service/logout")
    public Result<Void> logout(@RequestParam(required = false) String accessToken) {
        userLoginService.logout(accessToken);
        return Results.success();
    }
}
