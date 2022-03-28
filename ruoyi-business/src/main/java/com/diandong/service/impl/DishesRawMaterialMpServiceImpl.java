package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesRawMaterialPO;
import com.diandong.mapper.DishesRawMaterialMapper;
import com.diandong.service.DishesRawMaterialMpService;
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
public class DishesRawMaterialMpServiceImpl extends CommonServiceImpl<DishesRawMaterialMapper, DishesRawMaterialPO>
        implements DishesRawMaterialMpService {

}
