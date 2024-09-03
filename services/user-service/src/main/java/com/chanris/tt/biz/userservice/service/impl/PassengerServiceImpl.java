package com.chanris.tt.biz.userservice.service.impl;

import com.chanris.tt.biz.userservice.dto.req.PassengerRemoveReqDTO;
import com.chanris.tt.biz.userservice.dto.req.PassengerReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.PassengerActualRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.PassengerRespDTO;
import com.chanris.tt.biz.userservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 乘车人接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {



    @Override
    public List<PassengerRespDTO> listPassengerQueryByUsername(String username) {
        return null;
    }

    @Override
    public List<PassengerActualRespDTO> listPassengerQueryByIds(String username, List<Long> ids) {
        return null;
    }

    @Override
    public void savePassenger(PassengerReqDTO requestParam) {

    }

    @Override
    public void updatePassenger(PassengerReqDTO requestParam) {

    }

    @Override
    public void removePassenger(PassengerRemoveReqDTO requestParam) {

    }
}
