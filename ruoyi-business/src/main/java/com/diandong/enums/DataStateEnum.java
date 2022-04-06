package com.diandong.enums;

public enum DataStateEnum {
    DELETE_NO(0, "未删除"),
    DELETE_YES(1, "已删除"),
    ;


    private Integer value;
    private String info;

    DataStateEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (DataStateEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static DataStateEnum getDataStateEnum(Integer value) {

        for (DataStateEnum dataStateEnum : values()) {
            if (dataStateEnum.value == value) {
                return dataStateEnum;
            }
        }
        return null;
    }
}
