package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.CanteenPurchasePO;
import com.diandong.domain.vo.CanteenPurchaseVO;
import com.diandong.mapper.CanteenPurchaseMapper;
import com.diandong.mapstruct.CanteenPurchaseMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.CanteenPurchaseMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * 食堂采购service实现类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CanteenPurchaseMpServiceImpl extends CommonServiceImpl<CanteenPurchaseMapper, CanteenPurchasePO>
        implements CanteenPurchaseMpService {
    @Resource
    private CanteenMpService canteenMpService;

    @Override
    public BaseResult saveCanteenPurchase(CanteenPurchaseVO vo) {

        CanteenPO canteen = canteenMpService.getById(SecurityUtils.getCanteenId());
        if (Objects.isNull(canteen)) {
            return BaseResult.error("不能添加食谱采购");
        }

        LocalDate recipeStartDate = vo.getRecipeStartDate();
        LocalDate recipeEndDate = vo.getRecipeEndDate();


        if (recipeStartDate.compareTo(recipeEndDate) == 0) {
            Integer count = lambdaQuery()
                    .ge(CanteenPurchasePO::getRecipeStartDate, recipeStartDate)
                    .le(CanteenPurchasePO::getRecipeEndDate, recipeEndDate)
                    .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
                    .count();
            if (count > 0) {
                return BaseResult.error("食谱已经添加采购");
            }
        } else if (recipeStartDate.compareTo(recipeEndDate) > 0) {
            return BaseResult.error("日期传入有误");
        }

        int days = Period.between(recipeStartDate, recipeEndDate).getDays();

//        判断
        while (recipeStartDate.isBefore(recipeEndDate) || recipeStartDate.isEqual(recipeEndDate)) {
            Integer count = lambdaQuery()
                    .ge(CanteenPurchasePO::getRecipeStartDate, recipeStartDate)
                    .le(CanteenPurchasePO::getRecipeEndDate, recipeStartDate)
                    .eq(CanteenPurchasePO::getDelFlag, Constants.DEL_NO)
                    .count();
            if (count != 0) {
                days -= 1;
            }

            recipeStartDate = recipeStartDate.plusDays(1L);

        }


        CanteenPurchasePO canteenPurchasePO = CanteenPurchaseMsMapper.INSTANCE.vo2po(vo);
        canteenPurchasePO.setCanteenId(canteen.getId());
        canteenPurchasePO.setCanteenName(canteen.getCanteenName());
        canteenPurchasePO.setDays(days);
        save(canteenPurchasePO);

        return BaseResult.success();
    }
}
