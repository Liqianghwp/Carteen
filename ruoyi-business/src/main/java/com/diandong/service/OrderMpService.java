package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.OrderPO;
import com.diandong.domain.vo.OrderVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * Service接口类
 *
 * @author YuLiu
 * @date 2022-03-30
 */
public interface OrderMpService extends CommonService<OrderPO> {


    /**
     * 生成订单
     *
     * @return BaseResult
     */
    BaseResult createOrder();

    /**
     * 取消订单
     *
     * @param orderId   订单id
     * @param loginUser 登录用户
     * @return BaseResult
     */
    BaseResult cancelOrder(Long orderId, LoginUser loginUser);

    /**
     * 支付订单
     *
     * @param orderVO   订单信息
     * @return
     */
    BaseResult payOrder(OrderVO orderVO);

    /**
     * 处理订单信息
     *
     * @param orderId   订单编号
     * @param status    订单状态
     * @return BaseResult
     */
    BaseResult processOrders(Long orderId, Integer status);

    /**
     * 获取订单详情
     *
     * @param orderId 订单id
     * @return BaseResult
     */
    BaseResult getOrderDetail(Long orderId);
}
