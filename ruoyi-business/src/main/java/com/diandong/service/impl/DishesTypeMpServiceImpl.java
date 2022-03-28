package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesTypePO;
import com.diandong.mapper.DishesTypeMapper;
import com.diandong.service.DishesTypeMpService;
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
public class DishesTypeMpServiceImpl extends CommonServiceImpl<DishesTypeMapper, DishesTypePO>
        implements DishesTypeMpService {

}
