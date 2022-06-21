package com.diandong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.diandong.constant.Constants;
import com.diandong.domain.po.OrderPO;
import com.diandong.domain.po.PayRecordsPO;
import com.diandong.domain.po.RechargeAmountRecordsPO;
import com.diandong.domain.po.RechargeTimesRecordsPO;
import com.diandong.enums.OrderStatusEnum;
import com.diandong.service.OrderMpService;
import com.diandong.service.PayRecordsMpService;
import com.diandong.service.RechargeAmountRecordsMpService;
import com.diandong.service.RechargeTimesRecordsMpService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.pay.model.enums.BizTypeEnum;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.vo.AliPayCallBackVO;
import com.ruoyi.pay.model.vo.PayResultEvenVO;
import com.ruoyi.pay.model.vo.UnionPayCallBackVO;
import com.ruoyi.pay.service.PayFinishService;
import com.ruoyi.pay.util.URLEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Classname PayFinishServiceImpl
 * @Description 支付完成实现类
 * @Date 2022/6/17 10:58
 * @Created by YuLiu
 */
@Slf4j
@Service
public class PayFinishServiceImpl implements PayFinishService {

    @Resource
    private PayRecordsMpService payRecordsMpService;
    @Resource
    private OrderMpService orderMpService;
    @Resource
    private RechargeTimesRecordsMpService rechargeTimesRecordsMpService;
    @Resource
    private RechargeAmountRecordsMpService rechargeAmountRecordsMpService;

    @Override
    public void advancePaySuccess(ReqPayVO payVO) {

        log.info("保存支付记录开始 ======================== 》   Start");
        PayRecordsPO payRecords = new PayRecordsPO();

        payRecords.setSerialId(payVO.getOrderId());
        payRecords.setType(payVO.getType().getValue());
        payRecords.setPayAmount(payVO.getPayMoney());
        payRecords.setUserId(SecurityUtils.getUserId());
        payRecords.setPayWay(payRecords.getPayWay());
        payRecords.setPayNo(payVO.getRunningNum());

        payRecordsMpService.save(payRecords);
        log.info("保存支付记录开始 ======================== 》   End");
    }

    @Override
    public void payNotifyCallBackFinish(String params, PayResultEvenVO payResultEvenVO) {


        PayWay payWay = payResultEvenVO.getPayWay();
        String jsonString = null;
        String outTradeNo = null;
        switch (payWay) {
            case WECHAT_APP:
            case WECHAT_JS_API:
            case WECHAT_APPLET:
            case WECHAT_H5:
            case WECHAT_NATIVE:
                WxPayOrderNotifyResult wxPayOrderNotifyResult = payResultEvenVO.getWxPayOrderNotifyResult();
                outTradeNo = wxPayOrderNotifyResult.getOutTradeNo();


                jsonString = URLEncoderUtil.getURLDecoderString(wxPayOrderNotifyResult.getAttach());
                break;
            case ALI_APP:
            case ALI_PC:
            case ALI_WAP:
                AliPayCallBackVO aliPayCallBackVo = payResultEvenVO.getAliPayCallBackVo();
                outTradeNo = aliPayCallBackVo.getOut_trade_no();

                jsonString = URLEncoderUtil.getURLDecoderString(aliPayCallBackVo.getPassback_params());

                break;
            case UNION_PAY_APP:
            case UNION_PAY_WEB:
            case CLOUD_QUICK_PASS_WAP:
            case CLOUD_QUICK_PASS_WEB:
                UnionPayCallBackVO unionPayCallBackVO = payResultEvenVO.getUnionPayCallBackVO();
                outTradeNo = unionPayCallBackVO.getQueryId();
                jsonString = URLEncoderUtil.getURLDecoderString(unionPayCallBackVO.getReqReserved1());
                break;
            default:
                break;
        }

        if (StringUtils.isNotBlank(jsonString)) {
            ReqPayVO payVO = JSONObject.parseObject(jsonString, ReqPayVO.class);


            payRecordsMpService.lambdaUpdate()
                    .set(PayRecordsPO::getPayFlag, Constants.DEFAULT_YES)
                    .set(PayRecordsPO::getPayTime, LocalDateTime.now())
                    .eq(PayRecordsPO::getPayNo, payVO.getRunningNum())
                    .update();


            BizTypeEnum type = payVO.getType();

            switch (type) {
                case ORDER:
                    orderMpService.lambdaUpdate()
                            .set(OrderPO::getStatus, OrderStatusEnum.COMPLETED.value())
                            .set(OrderPO::getPaymentTime, LocalDateTime.now())
                            .eq(OrderPO::getId, payVO.getOrderId())
                            .update();
                    break;
                case RECHARGE_TIMES:
//                    充次记录
                    rechargeTimesRecordsMpService.lambdaUpdate()
                            .set(RechargeTimesRecordsPO::getDelFlag, Constants.DEL_YES)
                            .eq(RechargeTimesRecordsPO::getSerialNumber, payVO.getOrderId())
                            .update();
                    break;
                case RECHARGE_AMOUNT:
//                    充值记录
                    rechargeAmountRecordsMpService.lambdaUpdate()
                            .set(RechargeAmountRecordsPO::getDelFlag, Constants.DEL_YES)
                            .eq(RechargeAmountRecordsPO::getSerialNumber, payVO.getOrderId())
                            .update();
                    break;
                default:
                    break;
            }


        }

    }


}
