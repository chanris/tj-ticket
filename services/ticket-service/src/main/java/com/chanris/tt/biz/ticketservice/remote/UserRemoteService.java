package com.chanris.tt.biz.ticketservice.remote;

import com.chanris.tt.biz.ticketservice.remote.dto.PassengerRespDTO;
import com.chanris.tt.framework.starter.convention.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@FeignClient(value = "tt-user${unique-name:}-service", url = "${aggregation.remote-url:}")
public interface UserRemoteService {

    /**
     * 根据乘车人 ID 集合查询乘车人列表
     */
    @GetMapping("/api/user-service/inner/passenger/actual/query/ids")
    Result<List<PassengerRespDTO>> listPassengerQueryByIds(@RequestParam("username") String username, @RequestParam("ids") List<String> ids);
}

