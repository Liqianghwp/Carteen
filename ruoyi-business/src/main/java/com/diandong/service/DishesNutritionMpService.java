package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.DishesNutritionPO;
import com.diandong.domain.vo.DishesNutritionVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;

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
    Boolean saveList(List<DishesNutritionPO> dnList) throws Exception;

    /**
     * 主动批量保存菜品营养信息
     *
     * @param dnList    菜品营养集合
     * @param loginUser 登录信息
     * @return BaseResult
     * @throws Exception
     */
    BaseResult saveDishesNutritionList(List<DishesNutritionVO> dnList, LoginUser loginUser) throws Exception;

}
