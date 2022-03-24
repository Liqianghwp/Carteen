package com.diandong.configuration;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 公共mybatis-plus继承ServiceImpl
 * 增加for update sql注入
 * @author lingzhi
 * @date 2022/3/7
 */
public class CommonServiceImpl<M extends CommonMapper<T>, T> extends ServiceImpl<M, T> {

    public T getOneForUpdate(Wrapper<T> queryWrapper) {
        return baseMapper.selectOneForUpdate(queryWrapper);
    }

    public List<T> listForUpdate(Wrapper<T> queryWrapper) {
        return baseMapper.selectListForUpdate(queryWrapper);
    }
}
