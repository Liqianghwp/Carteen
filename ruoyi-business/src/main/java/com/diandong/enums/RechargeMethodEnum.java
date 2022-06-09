package com.diandong.enums;

/**
 * 充值方式
 */
public enum RechargeMethodEnum {
    SYSTEM("0", "系统充值"),
    APP("1", "APP充值"),
    TERMINAL("2", "终端充值"),
    ;


    private String type;
    private String name;

    RechargeMethodEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String type() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }


    public static RechargeMethodEnum getConfigEnum(String value) {

        for (RechargeMethodEnum rechargeMethodEnum : values()) {
            if (rechargeMethodEnum.type.equals(value)) {
                return rechargeMethodEnum;
            }
        }
        return null;
    }
}
