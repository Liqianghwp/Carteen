package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.domain.vo.RecipePieSituationVO;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
public interface RecipeDetailMpService extends CommonService<RecipeDetailPO> {


    /**
     * 派菜情况
     *
     * @param pieSituationVO
     */
    void pieSituation(RecipePieSituationVO pieSituationVO);

}
