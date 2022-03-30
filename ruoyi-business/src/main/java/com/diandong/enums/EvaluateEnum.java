package com.diandong.enums;

public enum EvaluateEnum {

//    WAIT_PAYMENT(0, "等待支付"),
//    COMPLETED(1, "已完成"),
//    CANCELLED(2, "已取消"),
    NOT_RATED(0, "未评价"),
    EVALUATED(1, "已评价"),
    ;


    private Integer value;
    private String info;

    EvaluateEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (EvaluateEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static EvaluateEnum getEvaluateEnum(Integer value) {

        for (EvaluateEnum evaluateEnum : values()) {
            if (evaluateEnum.value == value) {
                return evaluateEnum;
            }
        }
        return null;
    }
}
