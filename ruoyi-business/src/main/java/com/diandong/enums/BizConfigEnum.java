package com.diandong.enums;

public enum BizConfigEnum {
    REGISTER_USER("注册开关", "sys.account.registerUser"),
    HEALTH_CERT_INVALID("健康证到期提醒", "HEALTH_CERT_INVALID"),
    MEAL_TIMES_INTERVAL("用餐次数间隔时间", "MEAL_TIMES_INTERVAL"),
    ;


    private String name;
    private String key;

    BizConfigEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String key() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }




    public static BizConfigEnum getConfigEnum(String value) {

        for (BizConfigEnum bizConfigEnum : values()) {
            if (bizConfigEnum.key.equals(value)) {
                return bizConfigEnum;
            }
        }
        return null;
    }
}
