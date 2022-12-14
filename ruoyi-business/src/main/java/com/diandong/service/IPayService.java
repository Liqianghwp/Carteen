package com.diandong.service;

import com.diandong.domain.po.UserAmountPO;

import java.math.BigDecimal;

/**
 * @Classname IPayService
 * @Description 异步支付设置
 * @Date 2022/6/16 11:13
 * @Created by YuLiu
 */
public interface IPayService {


    /**
     * 实体卡、电子卡支付异步调用
     *
     * @param orderPrice    订单价格
     * @param userAmount    用户金额
     */
    void cardPayTask(BigDecimal orderPrice, UserAmountPO userAmount);

    /**
     * 人脸认证支付异步调用
     *
     * @param orderPrice    订单价格
     * @param userAmount    用户金额
     */
    void faceEnginePayTask(BigDecimal orderPrice, UserAmountPO userAmount);

}
