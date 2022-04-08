package com.diandong.constant;

/**
 * @author lingzhi
 * @date 2022/2/18
 */
public class Constants {
    /**
     * 逻辑删除字段名
     */
    public static final String LOGIC_DELETE_COLUMN = "is_del";
    /**
     * 顾问岗位code
     */
    public static final String POST_CODE_GW = "gw";

    /**
     * 顾问角色key
     */
    public static final String ROLE_KEY_GW = "gw";

    /**
     * 客户非公海名单标识
     */
    public static final Integer CUSTOMER_PUBLIC_DISABLE = 0;

    /**
     * 客户公海名单标识
     */
    public static final Integer CUSTOMER_PUBLIC_NORMAL = 1;

    /**
     * 条形码已使用状态值
     */
    public static final Integer BARCODE_USE_STATUS_USE = 1;

    /**
     * 条形码未使用状态值
     */
    public static final Integer BARCODE_USE_STATUS_NOT_USE = 0;

    /**
     * 生成合同编号前缀
     */
    public static final String GENERATE_CONTRACT_CODE_PREFIX = "XMHT";

    /**
     * 生成知情书编号前缀
     */
    public static final String GENERATE_ZHIQING_CODE_PREFIX = "XMZQ";

    /**
     * 未登录信息
     */
    public static final String ERROR_MESSAGE = "用户未登录，无法进行操作。请您重新登录!";


}
