package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.DishesDTO;
import com.diandong.domain.dto.DishesNutritionDTO;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.po.DishesPO;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.mapper.DishesMapper;
import com.diandong.mapstruct.DishesNutritionMsMapper;
import com.diandong.mapstruct.DishesRawMaterialMsMapper;
import com.diandong.service.DishesMpService;
import com.diandong.service.DishesNutritionMpService;
import com.diandong.service.DishesRawMaterialMpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesMpServiceImpl extends CommonServiceImpl<DishesMapper, DishesPO>
        implements DishesMpService {

    @Resource
    private DishesNutritionMpService dishesNutritionMpService;
    @Resource
    private DishesRawMaterialMpService dishesRawMaterialMpService;


    @Override
    public void getAllDishMsg(DishesDTO dto) {

        Long dishId = dto.getId();

//        菜品营养信息
        List<DishesNutritionPO> dishesNutritionPOList = dishesNutritionMpService.lambdaQuery()
                .eq(DishesNutritionPO::getDishesId, dishId)
                .eq(DishesNutritionPO::getDelFlag, Constants.DEL_NO)
                .list();


        List<DishesRawMaterialPO> dishesRawMaterialPOList = dishesRawMaterialMpService.lambdaQuery()
                .eq(DishesRawMaterialPO::getDelFlag, Constants.DEL_NO)
                .eq(DishesRawMaterialPO::getDishesId, dishId)
                .list();

        List<DishesNutritionDTO> collect1 = dishesNutritionPOList.stream().map(dishesNutritionPO -> DishesNutritionMsMapper.INSTANCE.po2dto(dishesNutritionPO)).collect(Collectors.toList());
        List<DishesRawMaterialDTO> collect2 = dishesRawMaterialPOList.stream().map(dishesRawMaterialPO -> DishesRawMaterialMsMapper.INSTANCE.po2dto(dishesRawMaterialPO)).collect(Collectors.toList());

        dto.setDishesNutritionList(collect1);
        dto.setDishesRawMaterialList(collect2);


    }
}
