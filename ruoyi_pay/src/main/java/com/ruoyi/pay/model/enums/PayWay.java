package com.ruoyi.pay.model.enums;

import lombok.Getter;

/**
 * 支付通道途径
 *
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:25
 */
public enum PayWay implements BaseEnum {

    WECHAT_APP(2, "微信APP支付"),
    WECHAT_JS_API(3, "微信公众号支付"),
    ALI_APP(4, "支付宝APP支付"),
    ALI_PC(5, "支付宝PC支付"),
    ALI_WAP(6, "支付宝WAP支付"),
    WECHAT_APPLET(7, "小程序微信支付"),
    WECHAT_H5(8, "微信H5支付"),
    WECHAT_NATIVE(9, "微信原生支付"),
    UNION_PAY_WEB(10, "银联支付WEB"),
    CLOUD_QUICK_PASS_WEB(11, "云闪付WEB"),
    UNION_PAY_APP(12, "银联支付APP支付"),
    CLOUD_QUICK_PASS_WAP(13, "云闪付WAP支付"),

    ;

    @Getter
    private final Integer value;

    @Getter
    private final String description;

    PayWay(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PayWay valueOf(Integer value) {
        for (PayWay item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static PayWay getPayWayByValue(Integer value) {
        for (PayWay value1 : values()) {
            if (value1.value.equals(value)) {
                return value1;
            }
        }
        return null;
    }

    public static String getDescriptionByValue(Integer value) {
        String result = null;
        if (value == null) {
            return result;
        }
        for (PayWay item : PayWay.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescription();
            }
        }
        return result;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public PayWay get(Integer value) {
        return PayWay.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}
