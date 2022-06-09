package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.po.IngredientsPO;
import com.diandong.domain.vo.IngredientsVO;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 配料管理Service接口类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
public interface IngredientsMpService extends CommonService<IngredientsPO> {

    /**
     * 保存配料信息
     *
     * @param vo 配料信息
     * @return
     */
    BaseResult saveIngredients(IngredientsVO vo);

    /**
     * 更新配料信息
     *
     * @param vo 配料信息
     * @return
     */
    BaseResult updateIngredients(IngredientsVO vo);
}
