package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.vo.PurchasePlanVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * 采购计划单Service接口类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
public interface PurchasePlanMpService extends CommonService<PurchasePlanPO> {


    /**
     * 创建采购计划单
     *
     * @param applyId         审批单id
     * @param canteenPurchase 食谱采购
     */
    void createPurchasePlan(String applyId, CanteenPurchasePO canteenPurchase);

    /**
     * 通过id查询对应信息
     *
     * @param id
     * @return
     */
    BaseResult findPurchasePlan(Long id);

    /**
     * 保存
     *
     * @param vo 需要保存的数据
     * @return
     */
    BaseResult savePurchasePlan(PurchasePlanVO vo);

    /**
     * 审核
     * @param vo    需要审核的数据
     * @return
     */
    BaseResult audit(PurchasePlanVO vo);
}
