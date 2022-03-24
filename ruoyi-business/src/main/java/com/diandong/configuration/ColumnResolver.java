package com.diandong.configuration;

/**
 * @author lingzhi
 * @date 2022/3/15
 */

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @author lingzhi
 * @date 2022/3/15
 */
public class ColumnResolver<T> extends AbstractLambdaWrapper<T, ColumnResolver<T>> {

    @Override
    protected ColumnResolver<T> instance() {
        return null;
    }

    @Override
    public String columnsToString(SFunction<T, ?>... columns) {
        return super.columnsToString(columns);
    }

    @Override
    public String columnsToString(boolean onlyColumn, SFunction<T, ?>... columns) {
        return super.columnsToString(onlyColumn, columns);
    }

    @Override
    public String columnToString(SFunction<T, ?> column) {
        return super.columnToString(column);
    }

    @Override
    public String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
        return super.columnToString(column, onlyColumn);
    }
}
