package com.diandong.enums;


/**
 * 订单状态
 */
public enum OrderStatusEnum {

    WAIT_PAYMENT(0, "等待支付"),
    COMPLETED(1, "已完成"),
    CANCELLED(2, "已取消"),
//    NOT_RATED(0, "未评价"),
//    EVALUATED(1, "已评价"),
    ;


    private Integer value;
    private String info;

    OrderStatusEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (OrderStatusEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static OrderStatusEnum getOrderEnum(Integer value) {

        for (OrderStatusEnum orderStatusEnum : values()) {
            if (orderStatusEnum.value == value) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
