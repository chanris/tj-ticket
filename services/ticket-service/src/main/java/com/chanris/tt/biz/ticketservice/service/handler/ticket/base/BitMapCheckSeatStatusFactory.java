package com.chanris.tt.biz.ticketservice.service.handler.ticket.base;

import com.chanris.tt.framework.starter.bases.Singleton;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 座位通过 BitMap 检查抽象工厂
 */
public abstract class BitMapCheckSeatStatusFactory {

    public static final String TRAIN_BUSINESS = "TRAIN_BUSINESS"; // 商务类型

    public static final String TRAIN_FIRST = "TRAIN_FIRST"; // 一等

    public static final String TRAIN_SECOND = "TRAIN_SECOND"; // 二等

    public static BitMapCheckSeat getInstance(String mark) {
        BitMapCheckSeat instance = null;
        switch (mark) {
            case TRAIN_BUSINESS -> {
                // 获得对象
                instance = Singleton.get(TRAIN_BUSINESS);
                // 如果 instance == null ，则创建对象，并放入缓存中
                if (instance == null) {
                    instance = new TrainBusinessCheckSeat();
                    Singleton.put(TRAIN_BUSINESS, instance);
                }
            }
            case TRAIN_FIRST -> {
                instance = Singleton.get(TRAIN_FIRST);
                if (instance == null) {
                    instance = new TrainFirstCheckSeat();
                    Singleton.put(TRAIN_FIRST, instance);
                }
            }
            case TRAIN_SECOND -> {
                instance = Singleton.get(TRAIN_SECOND);
                if (instance == null) {
                    instance = new TrainSecondCheckSeat();
                    Singleton.put(TRAIN_SECOND, instance);
                }
            }
        }
        return instance;
    }
}
