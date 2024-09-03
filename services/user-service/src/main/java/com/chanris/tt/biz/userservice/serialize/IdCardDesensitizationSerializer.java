package com.chanris.tt.biz.userservice.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/3
 * @description 身份证号脱敏反序列化
 * todo 24/9/3
 */
public class IdCardDesensitizationSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String idCard, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String idCardDesensitization = DesensitizedUtil.idCardNum(idCard, 4, 4); // 从开头数到位置4，到末尾往前四个位置，中间用'*'代替。
        jsonGenerator.writeString(idCardDesensitization);
    }
}
