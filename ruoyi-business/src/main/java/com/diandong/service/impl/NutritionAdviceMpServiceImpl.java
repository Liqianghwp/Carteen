package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.mapper.NutritionAdviceMapper;
import com.diandong.service.NutritionAdviceMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NutritionAdviceMpServiceImpl extends CommonServiceImpl<NutritionAdviceMapper, NutritionAdvicePO>
        implements NutritionAdviceMpService {

}
