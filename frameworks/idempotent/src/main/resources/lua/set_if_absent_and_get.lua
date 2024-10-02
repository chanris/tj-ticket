-- 原子性获取给定key，若key存在返回其值，若key不存在则设置key并返回null
local key = KEYS[1]
local value = ARGV[1]
local expire_time_ms = ARGV[2]
-- 如果key不存在，则设置value，并返回null，设置过期时间，如果存在返回旧value
return redis.call('SET', key, value, 'NX', 'GET', 'PX', expire_time_ms)
