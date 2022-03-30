package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RecipePO;
import com.diandong.mapper.RecipeMapper;
import com.diandong.service.RecipeMpService;
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
public class RecipeMpServiceImpl extends CommonServiceImpl<RecipeMapper, RecipePO>
        implements RecipeMpService {

}
