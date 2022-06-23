package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.PurchasePlanPO;
import com.diandong.domain.po.PurchasingPO;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 采购清单管理Service接口类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
public interface PurchasingMpService extends CommonService<PurchasingPO> {

    /**
     * 创建采购清单
     * @param purchasePlan
     */
    void createPurchasing(PurchasePlanPO purchasePlan);


    /**
     * 采购
     * @param ids   id数组
     * @return
     */
    BaseResult purchase(Long[] ids);
}
