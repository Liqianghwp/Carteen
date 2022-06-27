package com.diandong.constant;

/**
 * @author lingzhi
 * @date 2022/2/18
 */
public class Constants {

    /**
     * 稳定出现一个
     */
    public static final String limit = "limit 1";

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

//    =============================补贴类型   START================================
    /**
     * 补贴发放
     */
    public static final Integer SUBSIDY_ISSUE = 0;
    /**
     * 补贴清零
     */
    public static final Integer SUBSIDY_CLEAR = 1;
//    =============================补贴类型   END================================


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
    /**
     * 记账常量
     * 1:是记账
     */
    public static final String TALLY_YES = "1";
    /**
     * 记账常量
     * 0:否记账
     */
    public static final String TALLY_NO = "0";

// ============================= 调拨管理常量 =============================

    /**
     * 调拨常量
     * 1:待审核
     */
    public static final String TO_AUDIT = "1";
    /**
     * 调拨常量
     * 2:审核驳回
     */
    public static final String REVIEW_THE_REJECTED = "2";
    /**
     * 调拨常量
     * 3:待出库
     */
    public static final String FOR_OUTBOUND = "3";
    /**
     * 调拨常量
     * 4:已入库
     */
    public static final String HAS_BEEN_PUT_IN_STORAGE = "4";
    /**
     * 调拨常量
     * 5:已出库
     */
    public static final String HAVE_OUTBOUND = "5";


//    ========================================= 获取验证码分类 Start
    /**
     * 旧手机号验证码
     */
    public static final String OLD_PHONE_VERIFY_CODE = "1";
    /**
     * 新手机号验证码
     */
    public static final String NEW_PHONE_VERIFY_CODE = "2";

//    ========================================= 获取验证码分类 End
//    ==========================================集团管理状态
    /**
     * 启用
     */
    public static final String START_USING = "0";
    /**
     * 停用
     */
    public static final String OUT_OF_SERVICE = "1";
}
