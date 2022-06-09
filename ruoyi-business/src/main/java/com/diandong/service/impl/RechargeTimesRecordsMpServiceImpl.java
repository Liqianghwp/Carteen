package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RechargeTimesRecordsPO;
import com.diandong.mapper.RechargeTimesRecordsMapper;
import com.diandong.service.RechargeTimesRecordsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充次记录service实现类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeTimesRecordsMpServiceImpl extends CommonServiceImpl<RechargeTimesRecordsMapper, RechargeTimesRecordsPO>
        implements RechargeTimesRecordsMpService {

}
