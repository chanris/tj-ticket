package com.chanris.tt.framework.starter.distributedid.core.snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description WorkId 包装器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkIdWrapper {
    /**
     * 工作ID
     */
    private Long workId;

    /**
     * 数据中心ID
     */
    private Long dataCenterId;
}
