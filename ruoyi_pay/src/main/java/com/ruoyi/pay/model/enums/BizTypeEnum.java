package com.ruoyi.pay.model.enums;

/**
 * @Classname BizTypeEnum
 * @Description 根据业务类型不同 内容不同
 * @Date 2022/6/17 9:29
 * @Created by YuLiu
 */
public enum BizTypeEnum implements BaseEnum {

    /**
     * 支付订单
     */
    ORDER(0, "订单支付"),
    /**
     * 充次支付
     */
    RECHARGE_TIMES(1, "充次"),
    /**
     * 充钱支付
     */
    RECHARGE_AMOUNT(2, "充钱"),
    ;

    private Integer value;
    private String description;

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Integer getValue() {
        return null;
    }

    BizTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }


    public static BizTypeEnum valueOf(Integer value) {
        for (BizTypeEnum item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

}
