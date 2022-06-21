package com.ruoyi.pay.model.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * 解决 spring cloud json 转枚举问题
 * @author xxxx
 * 2018年6月13日 下午5:30:02
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {
 
    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.currentName();
        Object currentValue = jp.getCurrentValue();
        Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        BaseEnum valueOf = BaseEnum.valueOf(node.asText(),findPropertyType);
        return valueOf;
    }
 
 
}