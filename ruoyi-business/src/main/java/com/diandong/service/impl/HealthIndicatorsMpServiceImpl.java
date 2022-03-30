package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.HealthIndicatorsPO;
import com.diandong.mapper.HealthIndicatorsMapper;
import com.diandong.service.HealthIndicatorsMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HealthIndicatorsMpServiceImpl extends CommonServiceImpl<HealthIndicatorsMapper, HealthIndicatorsPO>
        implements HealthIndicatorsMpService {

}
