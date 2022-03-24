package com.diandong.enums;

/**
 * 基础状态枚举类
 * @author lingzhi
 * @date 2022/3/14
 */
public enum BaseStatus {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 停用
     */
    DISABLE(-1, "停用");

    private Integer value;
    private String info;

    BaseStatus(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (BaseStatus item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
