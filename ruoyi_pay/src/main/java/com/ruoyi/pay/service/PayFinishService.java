package com.ruoyi.pay.service;

import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.req.ReqRefundVO;
import com.ruoyi.pay.model.req.ReqTransferAccountsVO;
import com.ruoyi.pay.model.res.ResTransferAccountsVO;
import com.ruoyi.pay.model.vo.AliPayCallBackVO;
import com.ruoyi.pay.model.vo.PayResultEvenVO;

/**
 * 支付完成 service
 *
 * @author : Lin
 * @version : v1.0
 * @createTime : [2022/1/21 0021 15:39]
 */
public interface PayFinishService {

    /**
     * 支付成功
     * 一般情况下需要 创建支付记录, 支付记录状态为 未支付
     * 余额支付时, 需要在此处同时处理 支付完成得情况
     *
     * @param payVO : 支付请求参数
     * @return : void
     * @Author LinGQ
     * @DATE 2022/1/21 0021 15:46
     */
    void advancePaySuccess(ReqPayVO payVO);

    /**
     * 支付 支付完成后回调
     * 由于 微信 和 支付宝 存在回拨机制, 需要自行验证 支付记录状态, 一般情况下 根据 aliPayCallBackVo.getOut_trade_no() 参数来确定一条支付记录
     * 一般情况下需要 更新支付记录, 支付记录状态为 已支付
     * 订单支付成功后得处理
     *
     * @param idOrderInfo     : 订单id
     * @param payResultEvenVO : 支付回调参数
     * @return : void
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:03
     */
    void payNotifyCallBackFinish(String idOrderInfo, PayResultEvenVO payResultEvenVO);



}
