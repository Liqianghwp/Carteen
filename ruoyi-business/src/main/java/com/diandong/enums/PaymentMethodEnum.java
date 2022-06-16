package com.diandong.enums;

/**
 * @description: 用户表 用户类型
 * @author: 滕玉静
 * @date: 2022-05-11
 */
public enum PaymentMethodEnum {
    FaceRecognition("FaceRecognition", "人脸识别"),
    WeChatPay("WeChatPay", "微信支付"),
    AliPay("AliPay", "支付宝支付"),
    Cash("Cash", "现金"),
    PhysicalCard("PhysicalCard", "实体卡"),
    ElectronicCard("ElectronicCard", "电子卡"),
    ;

    private String value;
    private String description;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 构造方法
     *
     * @param value
     * @param description
     */
    PaymentMethodEnum(String value, String description) {
        this.description = description;
        this.value = value;
    }

    public static PaymentMethodEnum getEnum(String value) {

        for (PaymentMethodEnum paymentMethodEnum : values()) {
            if (paymentMethodEnum.value.equals(value)) {
                return paymentMethodEnum;
            }
        }
        return null;
    }



}
