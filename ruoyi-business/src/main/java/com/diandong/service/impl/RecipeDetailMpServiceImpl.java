package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.mapper.RecipeDetailMapper;
import com.diandong.service.RecipeDetailMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecipeDetailMpServiceImpl extends CommonServiceImpl<RecipeDetailMapper, RecipeDetailPO>
        implements RecipeDetailMpService {

}
