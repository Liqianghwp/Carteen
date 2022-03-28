package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RawMaterialNutritionPO;
import com.diandong.mapper.RawMaterialNutritionMapper;
import com.diandong.service.RawMaterialNutritionMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RawMaterialNutritionMpServiceImpl extends CommonServiceImpl<RawMaterialNutritionMapper, RawMaterialNutritionPO>
        implements RawMaterialNutritionMpService {

}
