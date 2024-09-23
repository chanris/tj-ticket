-- KEYS 传递 Redis Key 值
local inputString = KEYS[2] -- 获取Redis命令的第二个参数 为luaScriptKey
local actualKey = inputString -- 初始化actualKey
local colonIndex = string.find(actualKey, ":") -- 找到":"的位置
if colonIndex ~= nil then -- 如果找到了":" 则截取 ":" 之后的部分作为actualKey
    actualKey = string.sub(actualKey, colonIndex + 1)
end
-- ARGV 传递脚本运行的其他参数  参数：{座位类型：座位个数}
local jsonArrayStr = ARGV[1]
-- 将JSON字符串解码成Lua表
-- 传来的时候为，JSON.toJSONString(jsonArray)
local jsonArray = cjson.decode(jsonArrayStr)

-- 初始化结果表
local result = {}
-- 初始化标记遍历
local tokenIsNull = false
-- 初始化表，用于存储票券不足的座位类型及其数量
-- lua 的表 可表示 数组 或者 映射(map)
-- 这里是用来表示数组
local tokenIsNullSeatTypeCounts = {}
-- ipairs 用来遍历表的 这里遍历解码后的的JSON数组
-- ipairs 返回 一个迭代器，迭代器返回两个值，第一个值是索引，第二个值是数组中的元素
for index, jsonObj in ipairs(jsonArray) do
    -- 获得需要的座位类型
    local seatType = tonumber(jsonObj.seatType)
    -- 获得需要的座位数量
    local count = tonumber(jsonObj.count)
    -- ".." 用于 连接两个字符串，类似java中的+号连接字符串
    local actualInnerHashKey = actualKey .. "_" .. seatType
     -- 获取对应的可用座位数量 hget: 意味 hash get, 传入 hash key field 获得 value
     -- KEYS[1] 为 列出的余票桶 redis key名称
    local ticketSeatAvailabilityTokenValue = tonumber(redis.call('hget', KEYS[1], tostring(actualInnerHashKey)))
    -- 如果可用票券数量小于0，则标记为true
    -- 需要的count 大于 redis记录的 可用座位数，则 tokenIsNull = true， 表示没有可用令牌
    if ticketSeatAvailabilityTokenValue < count then
        tokenIsNull = true
        -- 将座位类型及其数量添加到表
        -- table：用来操作表，lua脚本内置函数
        -- 余票不足map， key：座位类型， value：座位个数
        table.insert(tokenIsNullSeatTypeCounts, seatType .. "_" .. count)
    end
end
-- 将标记存入结果表
result['tokenIsNull'] = tokenIsNull
-- 如果有票卷不足的情况发生，则返回结果表
if tokenIsNull then
    -- 将票卷不足的信息存入结果表
    result['tokenIsNullSeatTypeCounts'] = tokenIsNullSeatTypeCounts
    -- 将结果转换为json字符串并返回
    return cjson.encode(result)
end
-- 获得Redis命令的第二个参数作为另一个JSON字符串
local alongJsonArrayStr = ARGV[2]
-- 解码第二个JSON字符串
local alongJsonArray = cjson.decode(alongJsonArrayStr)

-- 沿途经过的站点 扣减余票
for index, jsonObj in ipairs(jsonArray) do
    -- 获取座位类型
    local seatType = tonumber(jsonObj.seatType)
    -- 获取数量
    local count = tonumber(jsonObj.count)
    -- 遍历第二个解码后的JSON数组
    for indexTwo, alongJsonObj in ipairs(alongJsonArray) do
        -- 获得起点
        local startStation = tostring(alongJsonObj.startStation)
        -- 获得终点
        local endStation = tostring(alongJsonObj.endStation)
        -- 实际 redis key
        local actualInnerHashKey = startStation .. "_" .. endStation .. "_" .. seatType
        -- HINCRBY key field increment
        redis.call('hincrby', KEYS[1], tostring(actualInnerHashKey), -count)
    end
end

return cjson.encode(result)
