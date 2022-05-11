package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RechargeTimesPO;
import com.diandong.mapper.RechargeTimesMapper;
import com.diandong.service.RechargeTimesMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充值次数设置service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeTimesMpServiceImpl extends CommonServiceImpl<RechargeTimesMapper, RechargeTimesPO>
        implements RechargeTimesMpService {

}
