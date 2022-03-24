package com.diandong.configuration;

import org.springframework.stereotype.Component;

/**
 * 扩展继承mybatis plus,方便使用内部的lambda提取字段名方法
 * @author lingzhi
 * @date 2022/3/15
 */
@Component
public class MybatisPlusColumnResolver {
    public <T> ColumnResolver<T> create() {
        return new ColumnResolver<>();
    }
}
