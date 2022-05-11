package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.po.NutritionParamsPO;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.mapper.NutritionParamsMapper;
import com.diandong.service.DishesNutritionMpService;
import com.diandong.service.NutritionAdviceMpService;
import com.diandong.service.NutritionParamsMpService;
import com.diandong.service.RawMaterialNutritionMpService;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NutritionParamsMpServiceImpl extends CommonServiceImpl<NutritionParamsMapper, NutritionParamsPO>
        implements NutritionParamsMpService {

    @Resource
    private NutritionAdviceMpService nutritionAdviceMpService;
    @Resource
    private DishesNutritionMpService dishesNutritionMpService;
    @Resource
    private RawMaterialNutritionMpService rawMaterialNutritionMpService;


    @Override
    public void selectBeUsed(List<Long> idList) {

        for (Long aLong : idList) {

            NutritionParamsPO nutritionParams = getById(aLong);

            List<NutritionAdvicePO> naList = nutritionAdviceMpService.lambdaQuery()
                    .eq(NutritionAdvicePO::getNutritionalId, aLong)
                    .eq(NutritionAdvicePO::getDelFlag, false)
                    .list();
            List<DishesNutritionPO> dnList = dishesNutritionMpService.lambdaQuery()
                    .eq(DishesNutritionPO::getNutritionId, aLong)
                    .eq(DishesNutritionPO::getDelFlag, false)
                    .list();
            List<RawMaterialNutritionPO> rmnList = rawMaterialNutritionMpService.lambdaQuery()
                    .eq(RawMaterialNutritionPO::getNutritionParamsId, aLong)
                    .eq(RawMaterialNutritionPO::getDelFlag, false)
                    .list();


            if (CollectionUtils.isNotEmpty(naList) || CollectionUtils.isNotEmpty(dnList) || CollectionUtils.isNotEmpty(rmnList)) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", nutritionParams.getNutritionName()));
            }
        }
    }
}
