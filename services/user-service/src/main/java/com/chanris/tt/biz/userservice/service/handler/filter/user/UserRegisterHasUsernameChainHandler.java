package com.chanris.tt.biz.userservice.service.handler.filter.user;

import com.chanris.tt.biz.userservice.common.enums.UserRegisterErrorCodeEnum;
import com.chanris.tt.biz.userservice.dto.req.UserRegisterReqDTO;
import com.chanris.tt.biz.userservice.service.UserLoginService;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注册用户名唯一检验
 */
@Component
@RequiredArgsConstructor
public class UserRegisterHasUsernameChainHandler implements UserRegisterCreateChainFilter<UserRegisterReqDTO>{
    private final UserLoginService userLoginService;

    @Override
    public void handler(UserRegisterReqDTO requestParam) {
        if (!userLoginService.hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserRegisterErrorCodeEnum.HAS_USERNAME_NOTNULL);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
