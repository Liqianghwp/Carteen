package com.diandong.configuration;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lingzhi
 * @date 2022/3/8
 */
public interface CommonService<T> extends IService<T> {

    /**
     * 查询单条记录加行锁
     * @param queryWrapper
     * @return
     */
    T getOneForUpdate(Wrapper<T> queryWrapper);

    /**
     * 查询多条记录加行锁
     * @param queryWrapper
     * @return
     */
    List<T> listForUpdate(Wrapper<T> queryWrapper);
}
