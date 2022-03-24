package com.diandong.enums;

/**
 * 客户操作类型
 * @author lingzhi
 * @date 2022/2/17
 */
public enum CustomerOperateType {
    /**
     * 移入公海
     */
    ADD_PUBLIC(1, "移入公海"),
    /**
     * 移出公海
     */
    DEL_PUBLIC(2, "移出公海"),
    /**
     * 转移客户
     */
    MOVE(3, "转移客户"),
    /**
     * 删除
     */
    DEL(4, "删除");

    private Integer value;
    private String info;

    CustomerOperateType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (CustomerOperateType item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }
}
