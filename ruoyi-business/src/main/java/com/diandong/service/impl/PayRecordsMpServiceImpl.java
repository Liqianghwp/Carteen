package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.PayRecordsPO;
import com.diandong.mapper.PayRecordsMapper;
import com.diandong.service.PayRecordsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 支付记录service实现类
 *
 * @author YuLiu
 * @date 2022-06-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayRecordsMpServiceImpl extends CommonServiceImpl<PayRecordsMapper, PayRecordsPO>
        implements PayRecordsMpService {

}
