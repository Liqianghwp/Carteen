package com.ruoyi.pay.model.enums;


/**
 * 支付结果
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:24
 */
public enum PayResultType implements BaseEnum {

    INIT(0, "未支付"),
    SUCCESS(1, "成功"),
    REFUNDED(2,"已退款"),
    ;

    private final Integer value;

    private final String description;

    PayResultType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PayResultType valueOf(Integer value) {
        for (PayResultType item : values()) {
            if (item.getValue().equals (value)) {
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

    public PayResultType get(Integer value) {
        return PayResultType.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}