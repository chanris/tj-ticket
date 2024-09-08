package com.chanris.tt.biz.ticketservice.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车标签枚举
 */
@RequiredArgsConstructor
public enum TrainBrandEnum {
    GC_HIGH_SPEED_RAIL_INTERCITY("0", "GC-高铁/城际");

    @Getter
    private final String code;
    @Getter
    private final String name;

    /**
     * 根据编码查找名称
     */
    public static String findNameByCode(String code) {
        return Arrays.stream(TrainBrandEnum.values()) // todo 24/9/8
                .filter(each -> Objects.equals(each.getCode(), code))
                .findFirst()
                .map(TrainBrandEnum::getName)
                .orElse(null);
    }

    /**
     * 根据编码查找名称
     */
    public static List<String> findNameByCode(List<String> codes) {
        List<String> resultNames = new ArrayList<>();
        for (String code : codes) {
            String name = Arrays.stream(TrainBrandEnum.values())
                    .filter(each -> Objects.equals(each.getCode(), code))
                    .findFirst()
                    .map(TrainBrandEnum::getName)
                    .orElse(null);
            if (StrUtil.isNotBlank(name)) {
                resultNames.add(name);
            }
        }
        return resultNames;
    }
}
