package com.diandong.configuration;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.reflection.property.PropertyNamer;

/**
 * 反序列化获得数据库列名
 * @author lingzhi
 * @date 2022/3/15
 */
public class LambdaResolve {
    public static <T> String getColumn(SFunction<T, ?> func) {
        SerializedLambda lambda = LambdaUtils.resolve(func);
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,fieldName);
    }
}
