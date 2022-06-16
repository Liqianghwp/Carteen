package com.diandong.service.impl;

import com.diandong.domain.po.UserAmountPO;
import com.diandong.service.IPayService;
import com.diandong.service.UserAmountMpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Classname PayServiceImpl
 * @Description 支付异步处理
 * @Date 2022/6/16 11:16
 * @Created by YuLiu
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    @Resource
    private UserAmountMpService userAmountMpService;


    /**
     * 电子卡、实体卡支付任务
     */
    @Async
    @Override
    public void cardPayTask(BigDecimal orderPrice, UserAmountPO userAmount) {

        log.info("计算用户余额信息  ===> Start");
        if (userAmount.getSubsidy().compareTo(orderPrice) < 0) {
            userAmount.setAmount(userAmount.getAmount().subtract(orderPrice.subtract(userAmount.getSubsidy())));
            userAmount.setSubsidy(BigDecimal.ZERO);
        } else {
            userAmount.setSubsidy(userAmount.getSubsidy().subtract(orderPrice));
        }
        userAmountMpService.updateById(userAmount);
        log.info("计算用户余额信息  ===> End");
    }
}
