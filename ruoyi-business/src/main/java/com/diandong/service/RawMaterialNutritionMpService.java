package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.domain.vo.RawMaterialNutritionVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface RawMaterialNutritionMpService extends CommonService<RawMaterialNutritionPO> {


    /**
     * 添加原材料营养信息
     * @param voList
     * @return
     */
    BaseResult addList(List<RawMaterialNutritionVO> voList, LoginUser loginUser) throws Exception;

}
