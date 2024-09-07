package com.chanris.tt.biz.orderservice.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 手机号脱敏反序列化
 */
public class PhoneDesensitizationSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String phone, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String phoneDesensitization = DesensitizedUtil.mobilePhone(phone);
        gen.writeString(phoneDesensitization);
    }
}
