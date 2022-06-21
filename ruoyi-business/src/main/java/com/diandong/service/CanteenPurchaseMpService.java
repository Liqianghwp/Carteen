package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.vo.CanteenPurchaseVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * 食堂采购Service接口类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
public interface CanteenPurchaseMpService extends CommonService<CanteenPurchasePO> {

    /**
     * 保存食堂采购
     * @param vo
     * @return
     */
    BaseResult saveCanteenPurchase(CanteenPurchaseVO vo);

}
