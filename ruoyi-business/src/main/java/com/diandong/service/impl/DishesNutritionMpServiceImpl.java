package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.mapper.DishesNutritionMapper;
import com.diandong.service.DishesNutritionMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Boolean saveList(List<DishesNutritionPO> dnList) throws Exception {
        Boolean result =  false;

        for (DishesNutritionPO dishesNutritionPO : dnList) {

            result = save(dishesNutritionPO);

            if(!result){
                throw new Exception("保存菜品营养信息失败");
            }

        }



        return result;
    }
}
