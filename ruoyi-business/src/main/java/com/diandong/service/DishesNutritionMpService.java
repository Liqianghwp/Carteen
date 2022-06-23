package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.ruoyi.common.core.domain.BaseResult;

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
     *
     * @param dnList 菜品营养集合
     * @return BaseResult
     * @throws Exception
     */
    Boolean saveList(List<DishesNutritionPO> dnList);

    /**
     * 主动批量保存菜品营养信息
     *
     * @param dnList 菜品营养集合
     * @return BaseResult
     */
    BaseResult saveDishesNutritionList(List<DishesNutritionVO> dnList);

}
