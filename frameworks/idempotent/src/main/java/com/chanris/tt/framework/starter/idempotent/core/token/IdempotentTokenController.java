package com.chanris.tt.framework.starter.idempotent.core.token;

import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 基于 Token 验证请求幂等性控制器
 */
@RestController
@RequiredArgsConstructor
public class IdempotentTokenController {
    private final IdempotentTokenService idempotentTokenService;

    @GetMapping("/token")
    public Result<String> createToken() {
        return Results.success(idempotentTokenService.createToken());
    }
}
