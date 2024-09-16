package com.chanris.tt.biz.orderservice.remote;

import com.chanris.tt.biz.orderservice.remote.dto.UserQueryActualRespDTO;
import com.chanris.tt.framework.starter.convention.result.Result;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 用户远程调用服务
 */
@FeignClient(value = "tt-user${unique-name:}-service", url = "${aggregation.remote-url:}")
public interface UserRemoteService {
    @GetMapping("/api/user-service/actual/query")
    Result<UserQueryActualRespDTO> queryActualUserByUsername(@RequestParam("username") @NotEmpty String username);
}
