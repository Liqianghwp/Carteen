package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.BizDictPO;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.diandong.mapper.DishesNutritionMapper;
import com.diandong.mapstruct.DishesNutritionMsMapper;
import com.diandong.service.BizDictMpService;
import com.diandong.service.DishesMpService;
import com.diandong.service.DishesNutritionMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesNutritionMpServiceImpl extends CommonServiceImpl<DishesNutritionMapper, DishesNutritionPO>
        implements DishesNutritionMpService {

    @Resource
    private DishesMpService dishesMpService;
    @Resource
    private BizDictMpService dictMpService;


    @Override
    public Boolean saveList(List<DishesNutritionPO> dnList) {
        Boolean result = false;

        for (DishesNutritionPO dishesNutritionPO : dnList) {

            result = save(dishesNutritionPO);

            if (!result) {
                throw new ServiceException("保存菜品营养信息失败");
            }
        }
        return result;
    }


    @Override
    public BaseResult saveDishesNutritionList(List<DishesNutritionVO> dnList) {


        List<DishesNutritionPO> list = new ArrayList<>();
        for (DishesNutritionVO dishesNutritionVO : dnList) {

            DishesNutritionPO dishesNutritionPO = lambdaQuery()
                    .eq(DishesNutritionPO::getCanteenId, SecurityUtils.getCanteenId())
                    .eq(DishesNutritionPO::getDishesId, dishesNutritionVO.getDishesId())
                    .eq(DishesNutritionPO::getNutritionId, dishesNutritionVO.getNutritionId())
                    .eq(DishesNutritionPO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            if (Objects.isNull(dishesNutritionPO)) {
                if (StringUtils.isBlank(dishesNutritionVO.getDishesName())) {
                    DishesPO dishes = dishesMpService.getById(dishesNutritionVO.getDishesId());
                    dishesNutritionVO.setDishesName(Objects.nonNull(dishes) ? dishes.getDishesName() : null);
                }
                if (StringUtils.isBlank(dishesNutritionVO.getNutritionName())) {
                    BizDictPO bizdict = dictMpService.getById(dishesNutritionVO.getNutritionId());
                    dishesNutritionVO.setNutritionName(bizdict.getDictLabel());
                }
                dishesNutritionVO.setCanteenId(SecurityUtils.getCanteenId());
                dishesNutritionPO = DishesNutritionMsMapper.INSTANCE.vo2po(dishesNutritionVO);
            } else {
                dishesNutritionPO.setNumber(dishesNutritionVO.getNumber());
            }
            list.add(dishesNutritionPO);
        }

        saveOrUpdateBatch(list);

        return BaseResult.success();
    }
}
