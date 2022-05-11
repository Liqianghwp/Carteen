package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.mapper.HealthCertificateMapper;
import com.diandong.service.HealthCertificateMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 健康证service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HealthCertificateMpServiceImpl extends CommonServiceImpl<HealthCertificateMapper, HealthCertificatePO>
        implements HealthCertificateMpService {

}
