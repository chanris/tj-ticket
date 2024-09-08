package com.chanris.tt.biz.ticketservice.common.enums;

import cn.hutool.core.collection.ListUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 地区&站点类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum RegionStationQueryTypeEnum {
    HOT(0, null),
    A_E(1, ListUtil.of("A", "B", "C", "D", "E")),
    F_J(2, ListUtil.of("F", "G", "H", "J")),
    K_O(3, ListUtil.of("K", "L", "M", "N", "O")),
    P_T(4, ListUtil.of("P", "Q", "R", "S", "T")),
    U_Z(5, ListUtil.of("U", "V", "W", "X", "Y", "Z"));

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 拼音列表
     */
    private final List<String> spells;

    /**
     * 根据类型查找拼音集合
     */
    public static List<String> findSpellsByType(Integer type) {
        return Arrays.stream(RegionStationQueryTypeEnum.values())
                .filter(each -> Objects.equals(each.getType(), type))
                .findFirst()
                .map(RegionStationQueryTypeEnum::getSpells)
                .orElse(null);
    }
}
