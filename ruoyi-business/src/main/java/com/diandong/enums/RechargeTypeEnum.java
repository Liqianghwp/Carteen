package com.diandong.enums;

/**
 * 充值方式
 */
public enum RechargeTypeEnum {
    ELECTRONIC_CARD("0", "电子卡"),
    PHYSICAL_CARD("1", "实体卡"),
    ;


    private String type;
    private String name;

    RechargeTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String type() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }


    public static RechargeTypeEnum getConfigEnum(String value) {

        for (RechargeTypeEnum rechargeTypeEnum : values()) {
            if (rechargeTypeEnum.type.equals(value)) {
                return rechargeTypeEnum;
            }
        }
        return null;
    }
}
