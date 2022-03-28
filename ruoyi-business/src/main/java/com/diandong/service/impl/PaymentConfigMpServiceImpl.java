package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.PaymentConfigPO;
import com.diandong.mapper.PaymentConfigMapper;
import com.diandong.service.PaymentConfigMpService;
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
public class PaymentConfigMpServiceImpl extends CommonServiceImpl<PaymentConfigMapper, PaymentConfigPO>
        implements PaymentConfigMpService {

}
