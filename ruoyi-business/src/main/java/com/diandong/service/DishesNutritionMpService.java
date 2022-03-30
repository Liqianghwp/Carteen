package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.DishesNutritionPO;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
public interface DishesNutritionMpService extends CommonService<DishesNutritionPO> {

    /**
     * 批量保存菜品营养信息
     * @param dnList
     * @return
     */
    Boolean saveList(List<DishesNutritionPO> dnList) throws Exception;

}
