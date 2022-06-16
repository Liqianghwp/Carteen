package com.ruoyi.system.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname BizUserTypeConstants
 * @Description 业务的用户类型
 * @Date 2022/5/26 18:50
 * @Created by YuLiu
 */
public class BizUserTypeConstants {

    /**
     * 学生
     */
    public static final String STUDENT = "student";
    /**
     * 家长
     */
    public static final String PARENTS = "parents";
    /**
     * 食客
     */
    public static final String DINERS = "diners";
    /**
     * 员工
     */
    public static final String STAFF = "staff";
    /**
     * 系统默认的餐次设置
     */
    public static final List<String> defaultType = Arrays.asList(BizUserTypeConstants.STUDENT, BizUserTypeConstants.PARENTS);
}
