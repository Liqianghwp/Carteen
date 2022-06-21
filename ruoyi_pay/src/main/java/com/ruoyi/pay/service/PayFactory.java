package com.ruoyi.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.pay.model.common.PayServiceType;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.enums.RespEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * payService 工厂类
 *
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:31
 */
@Slf4j
@Component
@DependsOn(value = {"wechatPayServiceImpl", "aliPayServiceImpl", "unionPayServiceImpl"})
public class PayFactory {

    private Map<PayWay, PayService> payServiceMap = new ConcurrentHashMap<>(0);

    private ApplicationContext applicationContext;

    public PayFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init() {
        Map<String, PayService> res = applicationContext.getBeansOfType(PayService.class);
        if (!CollectionUtils.isEmpty(res)) {
            for (PayService payService : res.values()) {
                PayServiceType payServiceType = AnnotationUtils.findAnnotation(payService.getClass(), PayServiceType.class);
                if (payServiceType != null) {
                    PayWay[] payWays = payServiceType.payWay();
                    for (PayWay payWay : payWays) {
                        payServiceMap.put(payWay, payService);
                    }
                }
            }
        }
    }

    public PayService createPayService(PayWay payWay) {
        PayService payService = payServiceMap.get(payWay);
        if (payService != null) {
            return payService;
        }
        throw new RuntimeException(RespEnum.PAY_WAY_IS_NOT_FOUND.getDesc());
    }

}