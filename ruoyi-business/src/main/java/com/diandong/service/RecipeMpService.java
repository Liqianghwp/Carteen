package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.RecipePO;
import com.diandong.domain.vo.RecipeDetailVO;
import com.diandong.domain.vo.RecipeVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
     * @param vo        食谱信息
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
     * @param voList 菜谱菜品信息
     * @return
     */
    BaseResult rawMaterialsList(List<RecipeDetailVO> voList);

    /**
     * 不容食谱列表信息
     */
    void resetRecipeList(List<RecipePO> records);

}
