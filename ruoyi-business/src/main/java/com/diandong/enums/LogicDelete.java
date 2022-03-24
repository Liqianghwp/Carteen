package com.diandong.enums;


/**
 * 逻辑删除状态
 * @author lingzhi
 * @date 2022/2/14
 */
public enum LogicDelete {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DISABLE(1, "删除");

    private Integer value;
    private String info;

    LogicDelete(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (LogicDelete item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
