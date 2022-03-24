package com.diandong.configuration;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * mybatis-plus增加for update sql注入配置类
 * @author lingzhi
 * @date 2022/3/7
 */
public class SelectListForUpdate extends AbstractMethod {
    private static final String MAPPER_METHOD = "selectListForUpdate";
    private static final String SQL_TEMPLATE = "<script>%s SELECT %s FROM %s %s %s for update\n</script>";
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(SQL_TEMPLATE, sqlFirst(), sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo), sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, MAPPER_METHOD, sqlSource, tableInfo);
    }
}
