package com.diandong.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.OrderDTO;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.OrderVO;
import com.diandong.enums.OrderStatusEnum;
import com.diandong.enums.PaymentMethodEnum;
import com.diandong.enums.UserTypeEnum;
import com.diandong.mapper.OrderMapper;
import com.diandong.mapstruct.OrderDetailMsMapper;
import com.diandong.mapstruct.OrderMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.constant.BizUserTypeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderMpServiceImpl extends CommonServiceImpl<OrderMapper, OrderPO>
        implements OrderMpService {

    @Resource
    private OrderDetailMpService orderDetailMpService;
    @Resource
    private DishesMpService dishesMpService;
    @Resource
    private RecipeMpService recipeMpService;
    @Resource
    private RecipeDetailMpService recipeDetailMpService;
    @Resource
    private ShopCartMpService shopCartMpService;
    @Resource
    private BizDictMpService bizDictMpService;
    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private PaymentConfigMpService paymentConfigMpService;

    @Override
    public BaseResult createOrder() {

//        查询是否有未支付的订单
        Integer count = lambdaQuery().eq(OrderPO::getCreateBy, SecurityUtils.getUserId()).eq(OrderPO::getStatus, OrderStatusEnum.WAIT_PAYMENT.value()).eq(OrderPO::getDelFlag, Constants.DEL_NO).count();
        if (count > 0) {
            return BaseResult.error("您有待支付的订单，请完成待支付订单后再下单");
        }

//        获取就餐食堂id
        Long diningCanteenId = SecurityUtils.getLoginUser().getUser().getDiningCanteenId();
        CanteenPO canteen = canteenMpService.getById(diningCanteenId);
//        获取购物车信息
        List<ShopCartPO> list = shopCartMpService.lambdaQuery()
                .eq(ShopCartPO::getCanteenId, diningCanteenId)
                .eq(ShopCartPO::getDelFlag, Constants.DEL_NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return BaseResult.error("请您先选择菜品");
        }
//        查看是否符合当前餐次
        LocalTime localTime = LocalTime.now();

        Map<Long, List<ShopCartPO>> mealTimesMap = list.stream().collect(Collectors.groupingBy(ShopCartPO::getMealTimesId));

        for (Map.Entry<Long, List<ShopCartPO>> entry : mealTimesMap.entrySet()) {

            Long mealTimesId = entry.getKey();
            List<ShopCartPO> value = entry.getValue();

            BizDictPO bizDict = bizDictMpService.getById(mealTimesId);

            LocalTime beginTime = DateUtils.parseLocalTime(bizDict.getBeginTime());
            LocalTime endTime = DateUtils.parseLocalTime(bizDict.getEndTime());
            if (!(localTime.isAfter(beginTime) && localTime.isBefore(endTime))) {
                String dishesName = value.stream().map(ShopCartPO::getDishesName).collect(Collectors.joining(","));
                return BaseResult.error(dishesName + "；这些菜品无法在该时间就餐");
            }
        }
//        这些都是可以选用就餐的
        OrderPO orderPO = new OrderPO();

        orderPO.setId(bizIdUtil.getOrderId());
        orderPO.setCanteenId(diningCanteenId);
        orderPO.setCanteenName(canteen.getCanteenName());
        orderPO.setStatus(OrderStatusEnum.WAIT_PAYMENT.value());
        orderPO.setOrderTime(LocalDateTime.now());

        save(orderPO);

//        查询当天的食谱
        RecipePO recipe = recipeMpService.lambdaQuery()
                .eq(RecipePO::getCanteenId, diningCanteenId)
                .eq(RecipePO::getRecipeDate, LocalDate.now())
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .last(Constants.limit).one();


        BigDecimal orderTotalPrice = BigDecimal.ZERO;
        Integer dishesTotalCount = 0;

        List<OrderDetailPO> orderDetailList = new ArrayList<>();
        for (ShopCartPO shopCartPO : list) {
            DishesPO dishes = dishesMpService.getById(shopCartPO.getDishesId());

            OrderDetailPO orderDetailPO = new OrderDetailPO();

//            计算库存
            RecipeDetailPO recipeDetail = recipeDetailMpService.lambdaQuery()
                    .eq(RecipeDetailPO::getRecipeId, recipe.getId())
                    .eq(RecipeDetailPO::getMealTimesId, shopCartPO.getMealTimesId())
                    .eq(RecipeDetailPO::getDishesId, shopCartPO.getDishesId())
                    .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            if (recipeDetail.getNumber() < shopCartPO.getNumber()) {
                return BaseResult.error("菜品：" + shopCartPO.getDishesName() + ",库存不足");
            }

            recipeDetailMpService.update()
                    .set("number = number-", shopCartPO.getNumber())
                    .eq("recipe_id", recipe.getId())
                    .eq("meal_times_id", shopCartPO.getMealTimesId())
                    .eq("dishes_id", shopCartPO.getDishesId())
                    .eq("del_flag", Constants.DEL_NO);

            orderDetailPO.setOrderId(orderPO.getId());

            orderDetailPO.setMealTimesId(shopCartPO.getMealTimesId());
            orderDetailPO.setMealTimesName(shopCartPO.getMealTimesName());

            orderDetailPO.setDishesId(dishes.getId());
            orderDetailPO.setDishesName(dishes.getDishesName());
            orderDetailPO.setDishesPrice(checkUserType() ? dishes.getPrePrice() : dishes.getDishesPrice());
            orderDetailPO.setDishesCount(shopCartPO.getNumber());
            orderDetailPO.setDishesTotalPrice(orderDetailPO.getDishesPrice().multiply(BigDecimal.valueOf(orderDetailPO.getDishesCount())));
            orderDetailPO.setDishesPicture(dishes.getDishesPicture());

            orderDetailList.add(orderDetailPO);


            orderTotalPrice = orderTotalPrice.add(orderDetailPO.getDishesTotalPrice());
            dishesTotalCount += orderDetailPO.getDishesCount();
        }

//        清楚购物车内容
        List<Long> collect = list.stream().map(ShopCartPO::getId).collect(Collectors.toList());
        shopCartMpService.removeByIds(collect);


        orderDetailMpService.saveBatch(orderDetailList);

//        更新订单
        orderPO.setOrderTotalPrice(orderTotalPrice);
        orderPO.setDishesTotalCount(dishesTotalCount);
        updateById(orderPO);

//        返回订单信息
        OrderDTO order = OrderMsMapper.INSTANCE.po2dto(orderPO);
        order.setOrderDetailList(OrderDetailMsMapper.INSTANCE.poList2dtoList(orderDetailList));

        return BaseResult.success(order);
    }

    private Boolean checkUserType() {
        SysUser user = SecurityUtils.getLoginUser().getUser();

        Boolean checkState = false;
        String userType = user.getUserType();
        if (StringUtils.isNotBlank(userType)) {
            if (UserTypeEnum.SYSTEM.getValue().equals(userType)) {
                checkState = true;
            } else {
                BizDictPO bizDict = bizDictMpService.getById(userType);

                if (BizUserTypeConstants.DINERS.equals(bizDict.getDictValue())) {
                    checkState = true;
                }
            }
        }
        return checkState;
    }


    @Override
    public BaseResult cancelOrder(Long orderId, LoginUser loginUser) {

        OrderPO order = this.getById(orderId);

//        设置取消订单状态
        order.setStatus(OrderStatusEnum.CANCELLED.value());
//        设置更新人信息
        order.setUpdateBy(loginUser.getUserId());
        order.setUpdateTime(LocalDateTime.now());
//        更新订单状态
        boolean result = updateById(order);
        if (result) {
            return BaseResult.successMsg("取消订单成功");
        } else {
            return BaseResult.error("取消订单失败，请您重新操作!");
        }
    }

    @Override
    public BaseResult payOrder(OrderVO orderVO) {

        PaymentConfigPO paymentConfig = paymentConfigMpService.getById(orderVO.getPaymentMethodId());
        PaymentMethodEnum payMethod = PaymentMethodEnum.getEnum(paymentConfig.getPaymentMethod());

        switch (payMethod){
            case FaceRecognition:
//                人脸识别
                break;
            case WeChatPay:
//                微信支付
                break;
            case AliPay:
//                支付宝支付
                break;
            case Cash:
//                现金
                break;
            case PhysicalCard:
//                实体卡
                break;
            case ElectronicCard:
//                电子卡
                break;
            default:
                break;
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult processOrders(Long orderId, Integer status) {

        String message = "";
        OrderPO order = getById(orderId);
        //        设置取消订单状态
        order.setStatus(status);
        boolean result = updateById(order);

        switch (OrderStatusEnum.getOrderEnum(status)) {
//            支付订单
            case COMPLETED:

                if (result) {
//                  TODO 操作订单成功，走支付程序
//                    暂时默认不需要走支付程序


                    message = "订单支付成功";
                } else {
                    message = "订单支付失败，请重新操作";
                }
                break;
//            取消订单
            case CANCELLED:
                if (result) {
//                    返还库存信息
                    RecipePO recipe = recipeMpService.lambdaQuery()
                            .eq(RecipePO::getCanteenId, order.getCanteenId())
                            .eq(RecipePO::getRecipeDate, order.getOrderTime().toLocalDate())
                            .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                            .last(Constants.limit).one();
//                    查询订单详情
                    List<OrderDetailPO> orderDetailList = orderDetailMpService.lambdaQuery()
                            .eq(OrderDetailPO::getOrderId, order.getId())
                            .eq(OrderDetailPO::getDelFlag, Constants.DEL_NO)
                            .list();
                    if (CollectionUtils.isNotEmpty(orderDetailList)) {

                        for (OrderDetailPO orderDetail : orderDetailList) {

                            recipeDetailMpService.update()
                                    .set("number = number+", orderDetail.getDishesCount())
                                    .eq("order_id", recipe.getId())
                                    .eq("meal_times_id", orderDetail.getMealTimesId())
                                    .eq("dishes_id", orderDetail.getDishesId())
                                    .eq("del_flag", Constants.DEL_NO);
                        }
                    }

                    message = "订单取消成功";
                } else {
                    message = "订单取消失败，请您重新操作！";
                }
                break;
        }

        if (result) {
            return BaseResult.successMsg(message);
        } else {
            return BaseResult.error(message);
        }
    }

    @Override
    public BaseResult getOrderDetail(Long orderId) {

        OrderPO order = getById(orderId);

//        订单菜品列表信息
        List<OrderDetailPO> orderDetailList = orderDetailMpService.lambdaQuery()
                .eq(OrderDetailPO::getOrderId, orderId)
                .eq(OrderDetailPO::getDelFlag, false)
                .list();

        order.setOrderDetailList(orderDetailList);
        return BaseResult.success(order);
    }
}
