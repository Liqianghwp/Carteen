package com.ruoyi.pay.util;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

/**
 * @author Flying
 * @Description
 * @date 13:46 2018-10-17
 **/
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum extends IEnum<Integer> {

    String getDescription();

    @JsonValue
    @Override
    Integer getValue();

    /**
     * 反序列化
     *
     * @param enumType 实际枚举类型
     * @param value    当前值
     * @param <T>      枚举类型并且实现 {@link BaseEnum} 接口
     * @return 枚举常量
     */
    static <T extends BaseEnum> T valueOf(Object value, Class<T> enumType) {
        if (enumType == null || value == null) {
            return null;
        }

        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            Object enumValue = enumConstant.getValue();
            if (Objects.equals(enumValue, value)
                    || Objects.equals(enumValue.toString(), value.toString())) {
                return enumConstant;
            }
        }

        return null;
    }

}
