package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.NutritionParamsPO;

import java.util.List;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
public interface NutritionParamsMpService extends CommonService<NutritionParamsPO> {


    void  selectBeUsed(List<Long> idList);


}
