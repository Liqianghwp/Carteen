package com.diandong.enums;

/**
 * 报告生成状态
 * @author lingzhi
 * @date 2022/3/16
 */
public enum ReportStatus {
    NO(0, "未生成"),YES(1, "已生成");

    private Integer value;
    private String info;

    ReportStatus(Integer value, String info) {
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
        for (ReportStatus item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
