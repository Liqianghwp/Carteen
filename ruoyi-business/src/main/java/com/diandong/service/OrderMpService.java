package com.diandong.service;

import com.diandong.configuration.CommonService;
import com.diandong.domain.po.OrderPO;
import com.diandong.domain.vo.ShopCartVO;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
     * @param cartIds 购物车id集合
     * @return BaseResult
     */
    BaseResult createOrder(List<Long> cartIds, LoginUser loginUser) throws Exception;

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
     * @param orderId   订单编号
     * @param loginUser 当前登录人信息
     * @return
     */
    BaseResult payOrder(Long orderId, LoginUser loginUser);

    /**
     * 处理订单信息
     *
     * @param orderId   订单编号
     * @param loginUser 当前登录用户
     * @param status    订单状态
     * @return BaseResult
     */
    BaseResult processOrders(Long orderId, LoginUser loginUser, Integer status);

    /**
     * 获取订单详情
     *
     * @param orderId 订单id
     * @return BaseResult
     */
    BaseResult getOrderDetail(Long orderId);
}
