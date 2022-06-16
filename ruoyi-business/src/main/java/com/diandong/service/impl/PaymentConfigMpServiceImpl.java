package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.PaymentConfigPO;
import com.diandong.enums.PaymentMethodEnum;
import com.diandong.mapper.PaymentConfigMapper;
import com.diandong.service.PaymentConfigMpService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void initPaymentConfig() {

        Long canteenId = SecurityUtils.getCanteenId();

        List<PaymentConfigPO> list = new ArrayList<>();
        for (PaymentMethodEnum value : PaymentMethodEnum.values()) {
            PaymentConfigPO paymentConfig = new PaymentConfigPO();
            paymentConfig.setCanteenId(canteenId);
            paymentConfig.setPayway(value.getValue());
            paymentConfig.setPaymentMethod(value.getDescription());
            paymentConfig.setStatus(Constants.DEFAULT_YES);

            list.add(paymentConfig);
        }

        saveBatch(list);
    }
}
