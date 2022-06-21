package com.ruoyi.pay.model.enums;


/**
 * 支付通道类型
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:24
 */
public enum PayType implements BaseEnum {


	WXPAY(1, "微信支付"),
	ALIPAY(2, "支付宝支付"),
    ;

    private  Integer value;

    private  String description;

    PayType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PayType valueOf(Integer value) {
        for (PayType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public PayType get(Integer value) {
        return PayType.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}
