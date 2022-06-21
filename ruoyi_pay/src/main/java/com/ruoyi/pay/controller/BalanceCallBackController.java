package com.ruoyi.pay.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.service.PayFactory;
import com.ruoyi.pay.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 支付回调Controller
 *
 * @Author LinGQ
 * @DATE 2022/1/21 0021 16:48
 */
@RestController
@RequestMapping(value = "/balanceCallBack")
@Api(value = "支付回调Controller", tags = "支付回调")
@Slf4j
public class BalanceCallBackController extends BaseController {

    @Autowired
    private PayFactory payFactory;

    /**
     * 异步支付回调
     *
     * @return
     * @see com.ruoyi.pay.model.enums.PayWay
     */
    @RequestMapping(value = "/notifyUrl/{payWay}")
    @ApiOperation(value = "异步支付回调")
    public String notifyurl(@PathVariable(value = "payWay", required = true) Integer payWayValue) throws IOException {
        PayWay payWay = PayWay.valueOf(payWayValue);
        PayService payService = payFactory.createPayService(payWay);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return payService.payNotifyCallBack(request, payWay);
    }


//    /**
//     * 异步支付回调
//     *
//     * @return
//     * @see com.ruoyi.pay.model.enums.PayWay
//     */
//    @RequestMapping(value = "/notifyUrlNo/{payWay}")
//    @ApiOperation(value = "异步支付商家入驻业务回调")
//    public String notifyUrlNo(@PathVariable(value = "payWay", required = true) Integer payWayValue) throws IOException {
//        PayWay payWay = PayWay.valueOf(payWayValue);
//        PayService payService = payFactory.createPayService(payWay);
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        return payService.payNotifyShopCallBack(request, payWay);
//    }

    /**
     * 同步支付回调
     *
     * @return
     */
    @RequestMapping(value = "/{payWay}/returnUrl/{userClass}")
    @ApiOperation(value = "同步支付回调")
    public void returnUrl(@PathVariable(value = "payWay", required = true) Integer payWayValue, @PathVariable("userClass") Integer userClass) throws IOException {
        PayWay payWay = PayWay.valueOf(payWayValue);
        PayService payService = payFactory.createPayService(payWay);
        //验证签名
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String runningNumber = payService.verifyingSignatureReturnOrderNumber(request, payWay);
        response.setContentType("text/html;charset=utf-8");
        if (runningNumber != null) {
        } else {
            PrintWriter pw = response.getWriter();
            pw.println("验证签名失败");
        }
    }

}
