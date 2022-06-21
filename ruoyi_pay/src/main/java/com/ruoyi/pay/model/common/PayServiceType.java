package com.ruoyi.pay.model.common;

import com.ruoyi.pay.model.enums.PayWay;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayServiceType {

    /**
     * 支付类型
     * @return
     */
    PayWay[] payWay();

}
