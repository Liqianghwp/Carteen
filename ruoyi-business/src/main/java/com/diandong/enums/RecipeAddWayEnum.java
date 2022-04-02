package com.diandong.enums;


/**
 * 订单状态
 */
public enum RecipeAddWayEnum {

//    WAIT_PAYMENT(0, ""),
    DAY(1, "按天增加"),
    CYCLE(2, "周期性增加"),
//    NOT_RATED(0, "未评价"),
//    EVALUATED(1, "已评价"),
    ;


    private Integer value;
    private String info;

    RecipeAddWayEnum(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public static String getInfo(Integer value) {
        for (RecipeAddWayEnum item : values()) {
            if (item.value.equals(value)) {
                return item.info;
            }
        }
        return null;
    }


    public static RecipeAddWayEnum getAddWayEnum(Integer value) {

        for (RecipeAddWayEnum recipeAddWayEnum : values()) {
            if (recipeAddWayEnum.value == value) {
                return recipeAddWayEnum;
            }
        }
        return null;
    }
}
