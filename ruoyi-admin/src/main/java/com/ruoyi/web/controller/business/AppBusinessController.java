package com.ruoyi.web.controller.business;

import com.diandong.domain.vo.*;
import com.diandong.service.AppService;
import com.ruoyi.common.core.domain.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
     * @return
     */
    @ApiOperation(value = "用户钱包")
    @GetMapping("electronicCard")
    public BaseResult userAmount(){
        return appService.userAmount();
    }

    /**
     * 食堂充值设置
     * @return
     */
    @ApiOperation(value = "食堂充值设置")
    @GetMapping("canteen_recharge")
    public BaseResult getCanteenRecharge(){
        return appService.getCanteenRecharge();
    }

    /**
     * APP充值
     * @param vo    充值参数
     * @return
     */
    @ApiOperation(value = "APP充值")
    @GetMapping("APP充值")
    public BaseResult appRecharge(BackstageRechargeVO vo){
        return appService.appRecharge(vo);
    }

}
