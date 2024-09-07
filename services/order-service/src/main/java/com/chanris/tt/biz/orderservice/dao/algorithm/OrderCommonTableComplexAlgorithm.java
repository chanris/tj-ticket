package com.chanris.tt.biz.orderservice.dao.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description
 */
public class OrderCommonTableComplexAlgorithm implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        return null;
    }
}
