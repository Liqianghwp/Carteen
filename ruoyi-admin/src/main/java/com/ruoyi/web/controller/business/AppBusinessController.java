package com.ruoyi.web.controller.business;

import com.diandong.domain.vo.*;
import com.diandong.service.AppService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname AppBusinessController
 * @Description APP业务逻辑部分模块
 * @Date 2022/6/13 15:15
 * @Created by YuLiu
 */
@Validated
@RestController
@Api(value = "/app_business", tags = {"APP部分业务逻辑相关接口"})
@RequestMapping(value = "/app_business")
public class AppBusinessController {

    @Resource
    private AppService appService;


    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "APP:获取用户信息")
    @GetMapping("getUserInfo")
    public BaseResult getUserInfo() {
        return appService.getUserInfo();
    }

    /**
     * 找回密码：发送验证码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:发送验证码", notes = "发送验证码", httpMethod = "PUT")
    @PutMapping("/reset/get_code")
    public BaseResult getResetPwdVerifyCode(@RequestBody ResetPwdVO vo) {
        vo.setPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return appService.resetPwdVerifyCode(vo);
    }

    /**
     * 找回密码：校验验证码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:校验验证码", notes = "校验验证码，验证码不能为空", httpMethod = "PUT")
    @PutMapping("/reset/verify_code")
    public BaseResult resetVerifyPhoneCode(@RequestBody ResetPwdVO vo) {
        if (StringUtils.isBlank(vo.getVerifyCode())) {
            return BaseResult.error("验证码不能为空");
        }
        vo.setPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return appService.resetVerifyPhoneCode(vo);
    }

    /**
     * 找回密码：设置新密码
     *
     * @param vo 找回密码实体
     * @return
     */
    @ApiOperation(value = "找回密码:设置新密码", notes = "设置新密码，密码不能为空")
    @PostMapping("/reset/pwd")
    public BaseResult resetPwd(@RequestBody ResetPwdVO vo) {
        if (StringUtils.isBlank(vo.getPassword())) {
            return BaseResult.error("密码不能为空");
        }
        vo.setPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return appService.resetPwd(vo);
    }


    /**
     * 提交审核
     *
     * @return
     */
    @ApiOperation(value = "提交审核&重新提交审核")
    @PostMapping("submitReview")
    public BaseResult submitReview(@RequestBody SysUserVO vo) {
        return appService.submitReview(vo);
    }

    /**
     * 食堂列表
     *
     * @return
     */
    @ApiOperation(value = "APP:食堂列表")
    @GetMapping("canteen_list")
    public BaseResult canteenList() {
        return appService.canteenList();
    }

    /**
     * 设置就餐食堂
     *
     * @param canteenId 食堂id
     * @return
     */
    @ApiOperation(value = "设置就餐食堂")
    @GetMapping("set_dining_hall")
    public BaseResult setDiningHall(@RequestParam Long canteenId) {
        return appService.setDiningHall(canteenId);
    }

    /**
     * 获取就餐食堂轮播图
     *
     * @return
     */
    @ApiOperation(value = "获取就餐食堂轮播图")
    @GetMapping("canteen_carousel")
    public BaseResult getCanteenCarousel() {
        return appService.getCanteenCarousel();
    }

    /**
     * 当天食谱
     *
     * @return
     */
    @ApiOperation(value = "当天食谱")
    @GetMapping("todayRecipe")
    public BaseResult getTodayRecipe(@RequestParam Long canteenId) {
        return appService.getTodayRecipe(canteenId);
    }

    /**
     * 一周食谱
     *
     * @param canteenId
     * @return
     */
    @ApiOperation(value = "一周食谱")
    @GetMapping("weekRecipe")
    public BaseResult getWeekRecipe(@RequestParam Long canteenId) {
        return appService.getWeekRecipe(canteenId);
    }


//    菜品查询

    /**
     * 菜品查询
     *
     * @return
     */
    @ApiOperation(value = "查询菜品信息")
    @GetMapping("searchDishes")
    public BaseResult searchDishes(RecipeDetailVO vo) {
        return appService.searchDishes(vo);
    }

    /**
     * 菜谱详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "菜品详情", notes = "入参是食谱详情id，根据食谱详情id查询出菜品详情信息")
    @GetMapping("dishes/{id}")
    public BaseResult getDishesMessage(@PathVariable Long id) {
        return appService.getDishesMessage(id);
    }

    /**
     * 可用的APP支付通道
     *
     * @return
     */
    @ApiOperation(value = "APP支付通道")
    @GetMapping("payWay")
    public BaseResult getPayWay() {
        return appService.getPayWay();
    }

    /**
     * 订单支付
     *
     * @param orderPay
     * @return
     */
    @ApiOperation(value = "订单支付")
    @GetMapping("order_pay")
    public BaseResult pay(OrderPayVO orderPay) {
        return appService.pay(orderPay);
    }

    /**
     * 订单列表
     *
     * @return
     */
    @ApiOperation(value = "订单列表")
    @GetMapping("orderList")
    public BaseResult orderList(OrderVO vo) {
        return appService.orderList(vo);
    }

    /**
     * 用户钱包
     *
     * @return
     */
    @ApiOperation(value = "用户钱包")
    @GetMapping("electronicCard")
    public BaseResult userAmount() {
        return appService.userAmount();
    }

    /**
     * 食堂充值设置
     *
     * @return
     */
    @ApiOperation(value = "食堂充值设置")
    @GetMapping("canteen_recharge")
    public BaseResult getCanteenRecharge() {
        return appService.getCanteenRecharge();
    }

    /**
     * APP充值
     *
     * @param vo 充值参数
     * @return
     */
    @ApiOperation(value = "APP充值")
    @GetMapping("appRecharge")
    public BaseResult appRecharge(AppRechargeVO vo) {
        return appService.appRecharge(vo);
    }

    /**
     * 我的意见与反馈
     *
     * @return
     */
    @ApiOperation(value = "我的意见与反馈")
    @GetMapping("opinionFeedback")
    public BaseResult opinionFeedback(OpinionFeedbackVO vo) {
        return appService.opinionFeedback(vo);
    }


    /**
     * 学生支付方式
     *
     * @return
     */
    @ApiOperation(value = "学生支付方式")
    @GetMapping("studentPayWay")
    public BaseResult setStudentPayWay() {
        return appService.setStudentPayWay();
    }

    /**
     * 设置学生支付方式
     *
     * @return
     */
    @ApiOperation(value = "学生支付方式")
    @PostMapping("studentPayWay")
    public BaseResult setStudentPayWay(@RequestBody BusinessConfigVO vo) {
        return appService.setStudentPayWay(vo);
    }



}
