package com.chanris.tt.biz.ticketservice.common.constant;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description Redis Key 定义常量类
 */
public final class RedisKeyConstant {

    /**
     * 列车基本信息， Key Prefix + 列车ID
     */
    public static final String TRAIN_INFO = "tt-ticket-service:train_info:";

    /**
     * 地区与站点映射查询
     */
    public static final String REGION_TRAIN_STATION_MAPPING = "tt-ticket-service:region_train_station_mapping";

    /**
     * 站点查询分布式锁 key
     */
    public static final String LOCK_REGION_TRAIN_STATION_MAPPING = "tt-ticket-service:lock:region_train_station_mapping";

    /**
     * 站点查询， Key Prefix + 起始城市_终点城市_日期
     */
    public static final String REGION_TRAIN_STATION = "tt-ticket-service:region_train_station:%s_%s";

    /**
     * 站点查询分布式锁 key
     */
    public static final String LOCK_REGION_TRAIN_STATION = "tt-ticket-service:lock:region_train_station";

    /**
     * 地区以及站点查询 key
     */
    public static final String TRAIN_STATION_PRICE = "tt-ticket-service:train_station_price:%s_%s_%s";

    /**
     * 地区以及车站查询，Key Prefix + (车站名称 or 查询方法)
     */
    public static final String REGION_STATION = "tt-ticket-service:region-station:";

    /**
     * 列车车次剩余票量，Key Prefix + 车次ID
     */
    public static final String TRAIN_STATION_REMAINING_TICKET = "tt-ticket-service:train_station:remaining_ticket";

    /**
     * 列车车厢查询，Key Prefix + 列车ID
     */
    public static final String TRAIN_CARRIAGE = "tt-ticket-service:train_carriage:";

    /**
     * 列车线路信息查询，Key Prefix + 列车ID
     */
    public static final String TRAIN_STATION_STOPOVER_DETAIL = "tt-ticket-service:train_station_stopover_detail:";

    /**
     * 列车车厢状态，Key Prefix + 列车 ID + 起始站点 + 目的站点 + 车厢编号
     */
    public static final String TRAIN_CARRIAGE_SEAT_STATUS = "tt-ticket-service:train_carriage_seat_status";

    /**
     * 用户购票分布式锁 Key v2
     */
    public static final String LOCK_PURCHASE_TICKET_V2  = "${unique-name:}tt-ticket-service:lock:purchase_ticket_%s_%d";

    /**
     * 获取全部地点集合 Key
     */
    public static final String QUERY_ALL_REGIN_LIST = "tt-ticket-service:query_all_region_list";

    /**
     * 车厢余票查询，Key Prefix + 列车ID_起始站点_终点
     */
    public static final String TRAIN_STATION_CARRIAGE_REMAINING_TICKET = "tt-ticket-service:train_station_carriage_remaining_ticket:";

    /**
     * 列车购买令牌桶，Key Prefix + 列车ID
     */
    public static final String TICKET_AVAILABILITY_TOKEN_BUCKET = "tt-ticket-service:ticket_availability_token_bucket:";

    /**
     * 获取列车车厢数量集合分布式锁
     */
    public static final String LOCK_QUERY_CARRIAGE_NUMBER_LIST = "tt-ticket-service:lock:query_carriage_number_list_%s";

    /**
     * 获取地区以及站点集合分布式锁 Key
     */
    public static final String LOCK_QUERY_REGION_STATION_LIST = "tt-ticket-service:lock:query_region_station_list_%s";

    /**
     * 列车站点缓存
     */
    public static final String STATION_ALL = "tt-ticket-service:all_station";
    /**
     * 获取全部地点集合分布式锁 Key
     */
    public static final String LOCK_QUERY_ALL_REGION_LIST = "tt-ticket-service:lock:query_all_region_list";

    /**
     * 获取相邻座位余票分布式锁 Key
     */
    public static final String LOCK_SAFE_LOAD_SEAT_MARGIN_GET = "tt-ticket-service:lock:sage_load:seat_margin_%s";

    /**
     * 列车购买令牌桶加载数据 Key
     */
    public static final String LOCK_TICKET_AVAILABILITY_TOKEN_BUCKET = "tt-ticket-service:lock:ticket_availability_token_bucket:%s";

    /**
     * 令牌获取失败分布式锁
     */
    public static final String LOCK_TOKEN_BUCKET_ISNULL = "tt-ticket-service:lock:token-bucket-isnull:%s";
}

