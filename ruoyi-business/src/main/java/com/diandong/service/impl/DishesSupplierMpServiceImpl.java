package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesSupplierPO;
import com.diandong.mapper.DishesSupplierMapper;
import com.diandong.service.DishesSupplierMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 菜品供应商信息service实现类
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesSupplierMpServiceImpl extends CommonServiceImpl<DishesSupplierMapper, DishesSupplierPO>
        implements DishesSupplierMpService {

}
