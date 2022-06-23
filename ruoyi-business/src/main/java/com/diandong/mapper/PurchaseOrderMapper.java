package com.diandong.mapper;

import com.diandong.configuration.CommonMapper;
import com.diandong.domain.po.PurchaseOrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购订单管理Mapper
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Mapper
public interface PurchaseOrderMapper extends CommonMapper<PurchaseOrderPO> {

}