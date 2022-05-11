package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.mapper.HealthCertMsgMapper;
import com.diandong.service.HealthCertMsgMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 健康证到期消息service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HealthCertMsgMpServiceImpl extends CommonServiceImpl<HealthCertMsgMapper, HealthCertMsgPO>
        implements HealthCertMsgMpService {

}
