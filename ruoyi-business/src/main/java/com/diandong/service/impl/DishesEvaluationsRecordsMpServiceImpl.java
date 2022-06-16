package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesEvaluationsRecordsPO;
import com.diandong.mapper.DishesEvaluationsRecordsMapper;
import com.diandong.service.DishesEvaluationsRecordsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 菜品评价记录service实现类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesEvaluationsRecordsMpServiceImpl extends CommonServiceImpl<DishesEvaluationsRecordsMapper, DishesEvaluationsRecordsPO>
        implements DishesEvaluationsRecordsMpService {

}
