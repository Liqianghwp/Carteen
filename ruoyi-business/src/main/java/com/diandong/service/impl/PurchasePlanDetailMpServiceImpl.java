package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.PurchasePlanDetailPO;
import com.diandong.mapper.PurchasePlanDetailMapper;
import com.diandong.service.PurchasePlanDetailMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 采购计划单详情service实现类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchasePlanDetailMpServiceImpl extends CommonServiceImpl<PurchasePlanDetailMapper, PurchasePlanDetailPO>
        implements PurchasePlanDetailMpService {

}
