package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.ReadConstants;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.mapper.HealthCertMsgMapper;
import com.diandong.service.HealthCertMsgMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    @Override
    public HealthCertMsgPO haveRead(Long id) {
        HealthCertMsgPO healthCertMsgPO = getById(id);

        if (Objects.nonNull(healthCertMsgPO)) {
            healthCertMsgPO.setState(ReadConstants.READ_YES);
            updateById(healthCertMsgPO);
        }
        return healthCertMsgPO;

    }
}
