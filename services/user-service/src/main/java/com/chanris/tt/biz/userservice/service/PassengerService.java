package com.chanris.tt.biz.userservice.service;

import com.chanris.tt.biz.userservice.dto.req.PassengerRemoveReqDTO;
import com.chanris.tt.biz.userservice.dto.req.PassengerReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.PassengerActualRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.PassengerRespDTO;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 乘车人接口层
 */
public interface PassengerService {

    /**
     * 根据用户名查询乘车人列表
     *
     * @param username
     * @return
     */
    List<PassengerRespDTO> listPassengerQueryByUsername(String username);

    /**
     * 根据乘车人 ID 集合查询乘车人列表
     *
     * @param username
     * @param ids
     * @return
     */
    List<PassengerActualRespDTO> listPassengerQueryByIds(String username, List<Long> ids);

    /**
     * 新增乘车人
     *
     * @param requestParam
     */
    void savePassenger(PassengerReqDTO requestParam);

    /**
     * 修改乘车人
     *
     * @param requestParam
     */
    void updatePassenger(PassengerReqDTO requestParam);

    /**
     * 移除乘车人
     *
     * @param requestParam
     */
    void removePassenger(PassengerRemoveReqDTO requestParam);
}
