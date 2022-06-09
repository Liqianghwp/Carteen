package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.RechargeTimesConfigPO;
import com.diandong.domain.vo.RechargeTimesConfigVO;
import com.diandong.mapper.RechargeTimesConfigMapper;
import com.diandong.mapstruct.RechargeTimesConfigMsMapper;
import com.diandong.service.RechargeTimesConfigMpService;
import com.google.zxing.qrcode.encoder.QRCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充值次数设置service实现类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeTimesConfigMpServiceImpl extends CommonServiceImpl<RechargeTimesConfigMapper, RechargeTimesConfigPO>
        implements RechargeTimesConfigMpService {

    @Override
    public Boolean saveRechargeTimesConfig(RechargeTimesConfigVO vo) {
        RechargeTimesConfigPO rechargeTimesConfigPO = RechargeTimesConfigMsMapper.INSTANCE.vo2po(vo);


//        Qr








        return null;
    }
}
