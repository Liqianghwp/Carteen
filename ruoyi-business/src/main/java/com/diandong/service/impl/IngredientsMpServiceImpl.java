package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.IngredientsPO;
import com.diandong.mapper.IngredientsMapper;
import com.diandong.service.IngredientsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 配料管理service实现类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IngredientsMpServiceImpl extends CommonServiceImpl<IngredientsMapper, IngredientsPO>
        implements IngredientsMpService {

}
