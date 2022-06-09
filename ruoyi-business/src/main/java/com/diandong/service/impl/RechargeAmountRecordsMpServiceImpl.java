package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RechargeAmountRecordsPO;
import com.diandong.mapper.RechargeAmountRecordsMapper;
import com.diandong.service.RechargeAmountRecordsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充值记录service实现类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeAmountRecordsMpServiceImpl extends CommonServiceImpl<RechargeAmountRecordsMapper, RechargeAmountRecordsPO>
        implements RechargeAmountRecordsMpService {

}
