package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.PurchaseOrderPO;
import com.diandong.domain.po.PurchasingPO;
import com.diandong.domain.vo.PurchaseOrderVO;
import com.ruoyi.common.core.domain.BaseResult;

import java.util.List;

/**
 * 采购订单管理Service接口类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
public interface PurchaseOrderMpService extends CommonService<PurchaseOrderPO> {

    /**
     * 创建采购订单
     * @param vo  采购材料
     * @return
     */
    BaseResult createPurchaseOrder(PurchaseOrderVO vo);

    /**
     * 查询采购订单详情
     * @param id    采购订单id
     * @return
     */
    BaseResult selectPurchaseOrder(Long id);
}
