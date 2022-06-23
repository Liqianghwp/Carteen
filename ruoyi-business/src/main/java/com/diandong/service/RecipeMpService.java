package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.RecipePO;
import com.diandong.domain.vo.RecipeDetailVO;
import com.diandong.domain.vo.RecipeVO;
import com.ruoyi.common.core.domain.BaseResult;

import java.time.LocalDate;
import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
public interface RecipeMpService extends CommonService<RecipePO> {

    /**
     * 发布食谱
     *
     * @param vo 食谱信息
     * @return BaseResult
     */
    BaseResult recipePost(RecipeVO vo);

//    /**
//     * 食谱保存
//     *
//     * @param voList 食谱信息
//     * @return BaseResult
//     */
//    BaseResult saveList(List<RecipeVO> voList, LoginUser loginUser) throws Exception;


    BaseResult copyRecipe(Long id, LocalDate recipeDate);

    /**
     * 原材料清单
     *
     * @param recipeId 菜谱菜品信息
     * @return
     */
    BaseResult rawMaterialsList(String recipeId);

    /**
     * 原材料清单
     *
     * @param recipeId 食谱id
     * @return
     */
    BaseResult recipeSourcing(String recipeId);

    /**
     * 生成采购计划单
     *
     * @param recipeId 食谱id
     * @return
     */
    BaseResult createCanteenPurchase(String recipeId);

    /**
     * 不容食谱列表信息
     */
    void resetRecipeList(List<RecipePO> records);

}
