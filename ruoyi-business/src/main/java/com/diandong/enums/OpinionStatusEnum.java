package com.diandong.enums;

public enum OpinionStatusEnum {

    NOT_PROCESSED(0, "未处理"),
    VIEWED(1, "已查看"),
    PROCESSED(2, "已处理"),
    ;


    private Integer value;
    private String info;

    OpinionStatusEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (OpinionStatusEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static OpinionStatusEnum getOpinionStatusEnum(Integer value) {

        for (OpinionStatusEnum opinionStatusEnum : values()) {
            if (opinionStatusEnum.value == value) {
                return opinionStatusEnum;
            }
        }
        return null;
    }
}
