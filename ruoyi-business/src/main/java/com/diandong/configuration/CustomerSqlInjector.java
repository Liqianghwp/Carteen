package com.diandong.configuration;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * mybatis-plus增加for update sql注入配置类
 * @author lingzhi
 * @date 2022/3/7
 */
public class CustomerSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectOneForUpdate());
        methodList.add(new SelectListForUpdate());
        return methodList;
    }
}
