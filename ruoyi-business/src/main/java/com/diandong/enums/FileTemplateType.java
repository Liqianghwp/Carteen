package com.diandong.enums;

/**
 * 文件模板类型
 * @author lingzhi
 * @date 2022/2/14
 */
public enum FileTemplateType {
    /**
     * 报告
     */
    REPORT(0, "报告"),
    /**
     * 合同
     */
    CONTRACT(1, "合同"),
    /**
     * 知情书
     */
    INFORMED(2, "知情书");

    private Integer value;
    private String info;

    FileTemplateType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (FileTemplateType item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
