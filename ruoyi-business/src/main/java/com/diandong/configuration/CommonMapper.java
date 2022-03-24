package com.diandong.configuration;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公共mybatis-plus继承Mapper
 * 增加for update sql注入
 * @author lingzhi
 * @date 2022/3/7
 */
@Mapper
public interface CommonMapper<T> extends BaseMapper<T> {
    /**
     * 根据 entity 条件，查询一条记录并锁定
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    T selectOneForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    /**
     * 根据 entity 条件，查询全部记录并锁定
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    List<T> selectListForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
