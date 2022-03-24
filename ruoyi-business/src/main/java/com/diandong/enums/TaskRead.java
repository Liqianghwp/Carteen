package com.diandong.enums;

/**
 * 任务解读状态
 * @author lingzhi
 * @date 2022/3/16
 */
public enum TaskRead {
    /**
     * 未解读
     */
    NO(0, "未解读"),
    /**
     * 已解读
     */
    YES(1, "已解读");

    private Integer value;
    private String info;

    TaskRead(Integer value, String info) {
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
        for (TaskRead item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
