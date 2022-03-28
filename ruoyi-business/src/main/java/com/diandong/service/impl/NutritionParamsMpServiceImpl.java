package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.NutritionParamsPO;
import com.diandong.mapper.NutritionParamsMapper;
import com.diandong.service.NutritionParamsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NutritionParamsMpServiceImpl extends CommonServiceImpl<NutritionParamsMapper, NutritionParamsPO>
        implements NutritionParamsMpService {

}
