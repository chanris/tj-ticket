package com.chanris.tt.biz.ticketservice.service.handler.ticket.base;

import com.chanris.tt.framework.starter.cache.DistributedCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 高铁商务验证坐标
 */
public class TrainBusinessCheckSeat implements TrainBitMapCheckSeat {

    /**
     * 高铁商务座是否存在检查方法
     *
     * @param key              缓存Key
     * @param convert          座位统计Map
     * @param distributedCache 分布式缓存接口
     * @return 判断座位是否存在 true or false
     */
    @Override
    public boolean checkSeat(final String key, HashMap<Integer, Integer> convert, DistributedCache distributedCache) {
        boolean flag = false;
        ValueOperations<String, String> opsForValue = ((StringRedisTemplate) distributedCache.getInstance()).opsForValue();
        AtomicInteger matchCount = new AtomicInteger(0);
        for (int i = 0; i < 3; i++) {
            int cnt = 0;
            if (convert.containsKey(i)) {
                for (int j = 0; j < 2; j++) {
                    Boolean bit = opsForValue.getBit(key, i + j * 3);
                    if (null != bit & bit) {
                        cnt = cnt + 1;
                    }
                    if (cnt == convert.get(i)) {
                        matchCount.getAndIncrement();
                        break;
                    }
                    if (cnt != convert.get(i)) {
                        break;
                    }
                }
                if (matchCount.get() == convert.size()) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 高铁商务座选择座位是否被占用 v2
     *
     * @param chooseSeatList 选择座位
     * @param actualSeats    座位状态数组
     * @param SEAT_Y_INT     坐标转换 Map
     * @return
     */
    @Override
    public boolean checkChooseSeat(List<String> chooseSeatList, int[][] actualSeats, Map<Character, Integer> SEAT_Y_INT) {
        boolean isExists = true;
        for (int i = 0; i < chooseSeatList.size(); i++) {
            if (chooseSeatList.size() == 1) {
                String chooseSeat = chooseSeatList.get(i);
                int seatX = Integer.parseInt(chooseSeat.substring(1));
                int seatY = SEAT_Y_INT.get(chooseSeat.charAt(0));
                if (actualSeats[seatX][seatY] != 0 && actualSeats[1][seatY] != 0) {
                    break;
                }
            } else {
                String chooseSeat = chooseSeatList.get(i);
                int seatX = Integer.parseInt(chooseSeat.substring(1));
                int seatY = SEAT_Y_INT.get(chooseSeat.charAt(0));
                if (actualSeats[seatX][seatY] != 0) {
                    isExists = false;
                    break;
                }
            }
        }
        return isExists;
    }
}
