package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.DishesTypePO;
import com.diandong.domain.vo.DishesTypeVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface DishesTypeMpService extends CommonService<DishesTypePO> {

    /**
     * 保存菜品类别
     *
     * @param vo
     * @return
     */
    BaseResult saveDishesType(DishesTypeVO vo);

}
