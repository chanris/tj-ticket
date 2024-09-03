package com.chanris.tt.biz.userservice.service.handler.filter.user;

import com.chanris.tt.biz.userservice.common.enums.UserChainMarkEnum;
import com.chanris.tt.biz.userservice.dto.req.UserRegisterReqDTO;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注册责任链过滤器
 */
public interface UserRegisterCreateChainFilter<T extends UserRegisterReqDTO> extends AbstractChainHandler<UserRegisterReqDTO> {

    @Override
    default String mark() {
        return UserChainMarkEnum.USER_REGISTER_FILTER.name();
    }
}
