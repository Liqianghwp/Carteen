package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesPO;
import com.diandong.mapper.DishesMapper;
import com.diandong.service.DishesMpService;
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
public class DishesMpServiceImpl extends CommonServiceImpl<DishesMapper, DishesPO>
        implements DishesMpService {

}
