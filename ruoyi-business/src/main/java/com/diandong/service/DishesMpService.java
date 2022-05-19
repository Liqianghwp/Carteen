package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.dto.DishesDTO;
import com.diandong.domain.po.DishesPO;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
public interface DishesMpService extends CommonService<DishesPO> {


    /**
     * 补充菜品信息
     * @param dto
     */
    void getAllDishMsg(DishesDTO dto);


}
