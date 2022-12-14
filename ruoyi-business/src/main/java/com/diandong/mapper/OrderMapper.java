package com.diandong.mapper;

import com.diandong.configuration.CommonMapper;
import com.diandong.domain.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Mapper
public interface OrderMapper extends CommonMapper<OrderPO> {

}