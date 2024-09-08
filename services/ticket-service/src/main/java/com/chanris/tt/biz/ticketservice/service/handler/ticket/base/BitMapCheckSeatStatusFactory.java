package com.chanris.tt.biz.ticketservice.service.handler.ticket.base;

import com.chanris.tt.framework.starter.bases.Singleton;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 座位通过 BitMap 检查抽象工厂
 */
public abstract class BitMapCheckSeatStatusFactory {

    public static final String TRAIN_BUSINESS = "TRAIN_BUSINESS";

    public static final String TRAIN_FIRST = "TRAIN_FIRST";

    public static final String TRAIN_SECOND = "TRAIN_SECOND";

    public static BitMapCheckSeat getInstance(String mark) {
        BitMapCheckSeat instance = null;
        switch (mark) {
            case TRAIN_BUSINESS -> {
                instance = Singleton.get(TRAIN_BUSINESS);
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
