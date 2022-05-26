package com.ruoyi.system.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname MealSettingConstants
 * @Description 固定餐次
 * @Date 2022/5/23 15:43
 * @Created by YuLiu
 */
public class MealSettingConstants {

    /**
     * 早餐
     */
    public static final String BREAKFAST = "breakfast";
    /**
     * 午餐
     */
    public static final String LUNCH = "lunch";
    /**
     * 晚餐
     */
    public static final String DINNER = "dinner";

    /**
     * 系统默认的餐次设置
     */
    public static final List<String> defaultMeals = Arrays.asList(MealSettingConstants.BREAKFAST, MealSettingConstants.LUNCH, MealSettingConstants.DINNER);
}
