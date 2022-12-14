package com.ruoyi.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Classname BizIdUtil
 * @Description 业务id流水号
 * @Date 2022/6/6 20:28
 * @Created by YuLiu
 */
@Component
public class BizIdUtil {

    private final static String REDIS_KEY = "business:serial:";
    private final static String pattern = "yyyyMMddHHmmss";
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取调拨单据编号
     *
     * @return
     */
    public String getTransferNo() {
        return "DB" + getTime() + nextValue(REDIS_KEY + "transfer");
    }

    /**
     * 采购单据编号
     *
     * @return
     */
    public String getPurchaseNo() {
        return "CG" + getTime() + nextValue(REDIS_KEY + "purchase");
    }

    /**
     * 采购计划单编号
     *
     * @return
     */
    public String getShoppingListNo() {
        return "JH" + getTime() + nextValue(REDIS_KEY + "shopping_list");
    }

    /**
     * 充钱流水号
     *
     * @return
     */
    public String getRechargeAmountNo() {
        return "CQ" + getTime() + nextValue(REDIS_KEY + "recharge_amount");
    }

    /**
     * 充次流水号
     *
     * @return
     */
    public String getRechargeTimesNo() {
        return "CC" + getTime() + nextValue(REDIS_KEY + "recharge_times");
    }

    /**
     * 审批id
     *
     * @return
     */
    public String getApplyId() {
        return "AI" + getTime() + nextValue(REDIS_KEY + "apply_id");
    }

    /**
     * 订单id
     *
     * @return
     */
    public Long getOrderId() {
        return Long.valueOf(getTime() + nextValue(REDIS_KEY + "order"));
    }

    /**
     * 创建支付订单
     *
     * @param businessType 业务类型
     * @param userId       用户id
     * @return
     */
    public String createPayOrderNumber(String businessType, Long userId) {
        return getCurrentTimeMillis() + businessType + userId;
    }

    /**
     * 当前时间戳
     *
     * @return
     */
    public String getCurrentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }


    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }


    private String nextValue(String key) {
        Long increment = redisTemplate.opsForValue().increment(key);
        return StringUtils.leftPad(String.valueOf(increment % 10000), 4, "0");
    }
}
