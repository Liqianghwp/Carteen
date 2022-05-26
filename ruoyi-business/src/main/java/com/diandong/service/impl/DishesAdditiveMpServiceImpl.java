package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesAdditivePO;
import com.diandong.mapper.DishesAdditiveMapper;
import com.diandong.service.DishesAdditiveMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 菜品添加剂信息service实现类
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesAdditiveMpServiceImpl extends CommonServiceImpl<DishesAdditiveMapper, DishesAdditivePO>
        implements DishesAdditiveMpService {

}
