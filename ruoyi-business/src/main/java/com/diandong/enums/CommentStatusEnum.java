package com.diandong.enums;


/**
 * 订单状态
 */
public enum CommentStatusEnum {

    WAIT_PAYMENT(0, "未处理"),
    COMPLETED(1, "已处理"),
//    CANCELLED(2, "已取消"),
//    NOT_RATED(0, "未评价"),
//    EVALUATED(1, "已评价"),
    ;


    private Integer value;
    private String info;

    CommentStatusEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (CommentStatusEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static CommentStatusEnum getCommentStatusEnum(Integer value) {

        for (CommentStatusEnum commentStatusEnum : values()) {
            if (commentStatusEnum.value == value) {
                return commentStatusEnum;
            }
        }
        return null;
    }
}
