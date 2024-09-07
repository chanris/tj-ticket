package com.chanris.tt.biz.orderservice.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 身份证号脱敏反序列化
 */
public class IdCardDesensitizationSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String idCard, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String phoneDesensitization = DesensitizedUtil.idCardNum(idCard, 4, 4);
        gen.writeString(phoneDesensitization);
    }
}
