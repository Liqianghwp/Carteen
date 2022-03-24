package com.diandong.enums;

/**
 * 性别
 * @author lingzhi
 * @date 2022/3/16
 */
public enum Sex {
    /**
     * 男
     */
    MALE(0, "男"),
    /**
     * 女
     */
    FEMALE(1, "女");

    private Integer value;
    private String info;

    Sex(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer value() {
        return this.value;
    }

    public String info() {
        return this.info;
    }

    public static String getInfo(Integer value) {
        for (Sex item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
