package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.dto.DishesRawMaterialDTO;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.domain.vo.DishesRawMaterialVO;
import com.ruoyi.common.core.domain.model.LoginUser;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
public interface DishesRawMaterialMpService extends CommonService<DishesRawMaterialPO> {


    /**
     * 批量保存菜品原材料信息
     *
     * @param voList 菜品原材料信息集合
     * @return Boolean
     */
    Boolean saveList(List<DishesRawMaterialVO> voList, LoginUser loginUser);



}
