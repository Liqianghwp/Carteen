package com.diandong.service;

import com.diandong.domain.vo.*;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * @Classname AppService
 * @Description app接口特殊方法
 * @Date 2022/6/13 11:20
 * @Created by YuLiu
 */
public interface AppService {

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @return
     */
    void getPhoneVerifyCode(String phone);

    /**
     * 手机号注册&登录
     *
     * @param phoneLogin 手机号登录信息
     * @return
     */
    BaseResult phoneRegisterAndLogin(PhoneRegisterAndLoginVO phoneLogin);

    /**
     * APP登录
     *
     * @param appLoginVO app用户登录信息
     * @return
     */
    BaseResult appLogin(AppLoginVO appLoginVO);

    /**
     * 更换手机号获取验证码
     *
     * @param phone 手机号
     */
    BaseResult getChangePhoneVerifyCode(String phone, String type);

    /**
     * @param oldPhoneCode 旧手机号验证码
     * @param newPhone     新手机号
     * @param newPhoneCode 新手机号验证码
     * @return
     */
    BaseResult changePhone(String oldPhoneCode, String newPhone, String newPhoneCode);

    /**
     * 找回密码：发送验证码
     *
     * @param vo
     * @return
     */
    BaseResult resetPwdVerifyCode(ResetPwdVO vo);

    /**
     * 找回密码：校验验证码
     *
     * @param vo
     * @return
     */
    BaseResult resetVerifyPhoneCode(ResetPwdVO vo);

    /**
     * 找回密码：重设密码
     *
     * @param vo
     * @return
     */
    BaseResult resetPwd(ResetPwdVO vo);

    /**
     * 获取用户信息
     *
     * @return
     */
    BaseResult getUserInfo();

    /**
     * 提交审核&重新提交审核
     *
     * @param vo 用户信息
     * @return
     */
    BaseResult submitReview(SysUserVO vo);

    /**
     * APP:食堂列表
     *
     * @return
     */
    BaseResult canteenList();

    /**
     * 设置就餐食堂id
     *
     * @param canteenId 食堂id
     * @return
     */
    BaseResult setDiningHall(Long canteenId);

    /**
     * 获取就餐食堂的轮播图
     *
     * @return
     */
    BaseResult getCanteenCarousel();

    /**
     * 获取当天食谱
     *
     * @param canteenId 食堂id
     * @return
     */
    BaseResult getTodayRecipe(Long canteenId);

    /**
     * 一周食谱
     *
     * @param canteenId
     * @return
     */
    BaseResult getWeekRecipe(Long canteenId);

    /**
     * 查询菜品信息
     *
     * @param vo 查询参数
     * @return
     */
    BaseResult searchDishes(RecipeDetailVO vo);

    /**
     * 查询菜品详情
     *
     * @param id) 菜品id
     * @return
     */
    BaseResult getDishesMessage(Long id);

    /**
     * 支付通道
     *
     * @return
     */
    BaseResult getPayWay();

    /**
     * 订单支付
     *
     * @param orderPay 支付条件
     * @return
     */
    BaseResult pay(OrderPayVO orderPay);

    /**
     * 获取个人订单消息
     *
     * @return
     */
    BaseResult orderList(OrderVO vo);

    /**
     * 获取个人电子会员卡信息
     *
     * @return
     */
    BaseResult userAmount();

    /**
     * 食堂充值设置
     *
     * @return
     */
    BaseResult getCanteenRecharge();

    /**
     * APP充值记录
     *
     * @param vo
     * @return
     */
    BaseResult appRecharge(AppRechargeVO vo);

    /**
     * 意见与反馈
     *
     * @param vo
     * @return
     */
    BaseResult opinionFeedback(OpinionFeedbackVO vo);

    /**
     * 获取学生支付方式
     *
     * @return
     */
    BaseResult setStudentPayWay();

    /**
     * 设置学生支付方式
     *
     * @return
     */
    BaseResult setStudentPayWay(BusinessConfigVO vo);


}
