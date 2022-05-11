package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.ShopCartDetailVO;
import com.diandong.domain.vo.ShopCartVO;
import com.diandong.enums.EvaluateEnum;
import com.diandong.enums.OrderStatusEnum;
import com.diandong.mapper.OrderMapper;
import com.diandong.mapstruct.ShopCartMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    //    订单详情service
    @Resource
    private OrderDetailMpService orderDetailMpService;
    //    菜品service
    @Resource
    private DishesMpService dishesMpService;
    //    购物车Service
    @Resource
    private ShopCartMpService shopCartMpService;
    //    购物车详情Service
    @Resource
    private ShopCartDetailMpService shopCartDetailMpService;

    @Override
    public BaseResult createOrder(ShopCartVO shopCartVO, LoginUser loginUser) {


//        查询购物车信息
        ShopCartPO shopCartPO = shopCartMpService.getById(shopCartVO.getId());
//        查询购物车详情
//        购物车详情id集合
        List<Long> shopCartDetailIds = shopCartVO.getShopCartDetailVOList().stream().map(ShopCartDetailVO::getId).collect(Collectors.toList());

        List<ShopCartDetailPO> detailPOList = shopCartDetailMpService.listByIds(shopCartDetailIds);

//        订单表
        OrderPO order = new OrderPO();

        order.setCanteenId(shopCartPO.getCanteenId());
        order.setCanteenName(shopCartPO.getCanteenName());
        order.setStatus(OrderStatusEnum.WAIT_PAYMENT.value());
//        下单时间（当前时间）
        order.setOrderTime(LocalDateTime.now());
        order.setEvaluationStatus(EvaluateEnum.NOT_RATED.value());
        order.setCreateBy(loginUser.getUserId());


        boolean result = save(order);
        if (!result) {
            return BaseResult.error("生成订单失败,请您重新下单");
        }
//        设置订单详情

        for (ShopCartDetailPO shopCartDetailPO : detailPOList) {

//           订单详情
            OrderDetailPO orderDetail = new OrderDetailPO();
//            设置订单id
            orderDetail.setOrderId(order.getId());
//            获取菜品id
            Long dishesId = shopCartDetailPO.getDishesId();

            DishesPO dishes = dishesMpService.getById(dishesId);

            orderDetail.setDishesId(dishesId);
            orderDetail.setDishesName(dishes.getDishesName());
            orderDetail.setDishesPrice(dishes.getDishesPrice());
            orderDetail.setDishesCount(shopCartDetailPO.getNumber());
//            计算当前菜品的总价格
            orderDetail.setDishesTotalPrice(orderDetail.getDishesPrice().multiply(BigDecimal.valueOf(orderDetail.getDishesCount())));
            orderDetail.setCreateBy(loginUser.getUserId());

            result = orderDetailMpService.save(orderDetail);
            if (!result) {
                throw new ServiceException("订单详情添加失败");
            }

//            更新购物车信息
            result = shopCartDetailMpService.removeById(shopCartDetailPO.getId());
            if (!result) {
                throw new ServiceException("订单详情添加失败");
            }

        }
        if (result) {
            shopCartMpService.removeById(shopCartPO);
        }

        return BaseResult.successMsg("添加成功");
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
    public BaseResult payOrder(Long orderId, LoginUser loginUser) {

        OrderPO order = getById(orderId);

        //        设置取消订单状态
        order.setStatus(OrderStatusEnum.COMPLETED.value());
//        设置更新人信息
        order.setUpdateBy(loginUser.getUserId());
        order.setUpdateTime(LocalDateTime.now());
//        更新订单状态
        boolean result = updateById(order);

        //        更新订单状态
        if (result) {
//            TODO 支付逻辑（目前还不清楚怎么处理）
//            暂时就跳过支付过程


            return BaseResult.successMsg("支付订单成功");
        } else {
            return BaseResult.error("支付订单失败，请您重新操作!");
        }
    }

    @Override
    public BaseResult processOrders(Long orderId, LoginUser loginUser, Integer status) {

        String message = "";
        OrderPO order = getById(orderId);
        //        设置取消订单状态
        order.setStatus(status);
//        设置更新人信息
        order.setUpdateBy(loginUser.getUserId());
        order.setUpdateTime(LocalDateTime.now());
//        更新订单状态
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
