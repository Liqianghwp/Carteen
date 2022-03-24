package com.diandong.enums;

/**
 * 系统图片类型
 * @author lingzhi
 * @date 2022/2/14
 */
public enum SystemPicType {
    /**
     * 系统启动
     */
    START(0, "系统启动"),
    /**
     * 轮播图
     */
    INDEX(1, "轮播图");

    private Integer value;
    private String info;

    SystemPicType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (SystemPicType item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
