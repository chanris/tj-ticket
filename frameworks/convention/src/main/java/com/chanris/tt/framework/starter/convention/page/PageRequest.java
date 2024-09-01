package com.chanris.tt.framework.starter.convention.page;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 分页请求对象
 */
@Data
public class PageRequest {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页显示条数
     */
    private Long size = 10L;
}
