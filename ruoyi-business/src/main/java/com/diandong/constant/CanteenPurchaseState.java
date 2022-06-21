package com.diandong.constant;

/**
 * @Classname CanteenPurchaseState
 * @Description 食堂采购审核状态
 * @Date 2022/6/20 17:08
 * @Created by YuLiu
 */
public class CanteenPurchaseState {

    /**
     * 未提交
     */
    public static final Integer UN_SUBMIT = 0;
    /**
     * 审核中
     */
    public static final Integer SUBMIT = 1;
    /**
     * 审核通过
     */
    public static final Integer AUDIT_YES = 2;
    /**
     * 审核驳回
     */
    public static final Integer AUDIT_NO = 3;


}
