package com.diandong.enums;

/**
 * 项目状态
 * @author lingzhi
 * @date 2022/3/16
 */
public enum ProjectStatus {
    /**
     * 默认
     */
    NORMAL(0, ""),
    /**
     * 已退费
     */
    TUI_FEI(1, "已退费"),
    /**
     * 寄存
     */
    JI_CUN(2, "寄存"),
    /**
     * 传染病不合格
     */
    BU_HE_GE(3, "传染病不合格");

    private Integer value;
    private String info;

    ProjectStatus(Integer value, String info) {
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
        for (ProjectStatus item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
