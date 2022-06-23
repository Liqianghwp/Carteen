package com.diandong.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.*;
import com.diandong.mapper.PurchasingMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 采购清单管理service实现类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchasingMpServiceImpl extends CommonServiceImpl<PurchasingMapper, PurchasingPO>
        implements PurchasingMpService {

    @Resource
    private PurchasePlanDetailMpService purchasePlanDetailMpService;
    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private RawMaterialMpService rawMaterialMpService;
    @Resource
    private PurchasePlanMpService purchasePlanMpService;

    @Override
    public void createPurchasing(PurchasePlanPO purchasePlan) {

        List<PurchasePlanDetailPO> list = purchasePlanDetailMpService.lambdaQuery()
                .eq(PurchasePlanDetailPO::getPlanId, purchasePlan.getPlanId())
                .eq(PurchasePlanDetailPO::getDelFlag, Constants.DEL_NO)
                .list();

        if (CollectionUtil.isNotEmpty(list)) {

            CanteenPO canteen = canteenMpService.getById(purchasePlan.getCanteenId());
//            集团id
            Long pId = canteen.getPId();
            for (PurchasePlanDetailPO purchasePlanDetailPO : list) {
                PurchasingPO purchasing = lambdaQuery()
                        .eq(PurchasingPO::getGroupId, pId)
                        .eq(PurchasingPO::getIsPurchase, Constants.DEFAULT_NO)
                        .eq(PurchasingPO::getRawMaterialId, purchasePlanDetailPO.getRawMaterialId())
                        .eq(PurchasingPO::getDelFlag, Constants.DEL_NO)
                        .last(Constants.limit).one();

                Double number = purchasePlanDetailPO.getNumber();
                BigDecimal currentPrice = purchasePlanDetailPO.getCurrentPrice();
                if(Objects.isNull(purchasing)){
                    purchasing = new PurchasingPO();
                    RawMaterialPO rawMaterial = rawMaterialMpService.getById(purchasePlanDetailPO.getRawMaterialId());

                    purchasing.setGroupId(pId);
                    purchasing.setCategoryId(rawMaterial.getCategoryId());
                    purchasing.setCategoryName(rawMaterial.getCategoryName());
                    purchasing.setRawMaterialId(rawMaterial.getId());
                    purchasing.setRawMaterialName(rawMaterial.getRawMaterialName());
                    purchasing.setNumber(number);
                    purchasing.setUnitId(rawMaterial.getUnitId());
                    purchasing.setUnitName(rawMaterial.getUnitName());
                    purchasing.setPrice(currentPrice);
                    purchasing.setSubtotal(currentPrice.multiply(BigDecimal.valueOf(number)));
                }else {
                    purchasing.setNumber(purchasing.getNumber()+number);
                    purchasing.setPrice(currentPrice);
                    purchasing.setSubtotal(currentPrice.multiply(BigDecimal.valueOf(purchasing.getNumber())));
                }
//                更新采购清单
                saveOrUpdate(purchasing);
            }
        } else {
            throw new ServiceException("创建采购清单失败");
        }


    }

    @Override
    public BaseResult purchase(Long[] ids) {

        List<PurchasingPO> list = lambdaQuery().in(PurchasingPO::getId, Arrays.asList(ids)).list();






        return null;
    }
}
