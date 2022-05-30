package com.diandong.constant;

/**
 * @author lingzhi
 * @date 2022/2/18
 */
public class Constants {

    /**
     * 通用是
     */
    public static final Integer DEFAULT_YES = 1;
    /**
     * 通用否
     */
    public static final Integer DEFAULT_NO = 0;

    /**
     * 已删除
     */
    public static final Integer DEL_YES = 1;
    /**
     * 未删除
     */
    public static final Integer DEL_NO = 0;

    public static final String ANONYMOUS = "匿名";
    /**
     * 匿名
     */
    public static final Integer ANONYMOUS_YES = 1;
    /**
     * 不匿名
     */
    public static final Integer ANONYMOUS_NO = 0;
    /**
     * 下架
     */
    public static final Integer SHELF_OFF = 0;
    /**
     * 上架
     */
    public static final Integer SHELF_ON = 1;

    /**
     * 未登录信息
     */
    public static final String ERROR_MESSAGE = "用户未登录，无法进行操作。请您重新登录!";

//    =============================用户审核状态   START================================
    /**
     * 审核中
     */
    public static final String USER_CHECKED = "0";
    /**
     * 审核通过
     */
    public static final String USER_CHECK_YES = "1";
    /**
     * 审核失败
     */
    public static final String USER_CHECK_NO = "2";
//    =============================用户审核状态   END================================

//    ============================= 黑白名单常量  =============================
    /**
     * 白名单
     */
    public static final String WHITELIST = "0";
    /**
     * 黑名单
     */
    public static final String BLACKLIST = "1";
//    ============================= 黑白名单常量  =============================

//    ============================= 配料类型常量  =============================
    /**
     * 主料
     */
    public static final String INGREDIENTS_MAIN = "0";
    /**
     * 辅料
     */
    public static final String INGREDIENTS_SECONDARY = "1";
//    ============================= 配料类型常量  =============================

}
