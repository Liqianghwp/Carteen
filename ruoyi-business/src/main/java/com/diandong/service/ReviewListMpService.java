package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.po.ReviewListPO;

/**
 * 审核列Service接口类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
public interface ReviewListMpService extends CommonService<ReviewListPO> {


    /**
     * 添加审核列表
     *
     * @param canteenPurchasePO 审核业务功能id
     */
    void canteenProcurementAudit(CanteenPurchasePO canteenPurchasePO);


    /**
     * 采购计划单管理提交审核
     *
     * @param purchasePlan 审核业务功能id
     */
    void purchasePlanSubmit(PurchasePlanPO purchasePlan);

    /**
     * 采购计划单管理审核
     *
     * @param purchasePlan 审核业务功能id
     */
    void purchasePlanAudit(PurchasePlanPO purchasePlan);

}
