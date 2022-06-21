package com.ruoyi.pay.service;

import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.req.ReqRefundVO;
import com.ruoyi.pay.model.req.ReqTransferAccountsVO;
import com.ruoyi.pay.model.res.ResPayResultVO;
import com.ruoyi.pay.model.res.ResRefundResultVO;
import com.ruoyi.pay.model.res.ResTransferAccountsVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付 service
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:35
 */
public interface PayService {

    /**
     * 创建预支付
     * 需要从回调里处理 支付成功的业务
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:17
     * @param payVO :
     * @return : com.ruoyi.pay.model.res.ResPayResultVO
     */
    ResPayResultVO createBalance(ReqPayVO payVO);

    /**
     * 退款
     * 需要自行处理 退款成功得业务
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:17
     * @param refundVO :
     * @return : com.ruoyi.pay.model.res.ResRefundResultVO
     */
    ResRefundResultVO refundBalance(ReqRefundVO refundVO);

    /**
     * 转账
     * 需要自行处理 转账成功得业务
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:17
     * @param reqTransferAccountsVO :
     * @return : com.ruoyi.pay.model.res.ResTransferAccountsVO
     */
    ResTransferAccountsVO transferAccounts(ReqTransferAccountsVO reqTransferAccountsVO);

    /**
     * 异步回调
     * @param request
     * @return
     */
    String payNotifyCallBack(HttpServletRequest request, PayWay payWay);



    /**
     * 验证签名并返回订单号 失败则返回null
     * @param request
     */
    String verifyingSignatureReturnOrderNumber(HttpServletRequest request, PayWay payWay);

}
