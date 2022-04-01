package com.diandong.enums;

public enum AnonymousEnum {
    ANONYMOUS_NO(0, "未匿名"),
    ANONYMOUS_YES(1, "匿名"),
    ;


    private Integer value;
    private String info;

    AnonymousEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (AnonymousEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static AnonymousEnum getOpinionStatusEnum(Integer value) {

        for (AnonymousEnum anonymousEnum : values()) {
            if (anonymousEnum.value == value) {
                return anonymousEnum;
            }
        }
        return null;
    }
}
