package com.ruoyi.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ruoyi.pay.model.common.PayServiceType;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.enums.RespEnum;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.req.ReqRefundVO;
import com.ruoyi.pay.model.req.ReqTransferAccountsVO;
import com.ruoyi.pay.model.res.ResPayResultVO;
import com.ruoyi.pay.model.res.ResRefundResultVO;
import com.ruoyi.pay.model.res.ResTransferAccountsVO;
import com.ruoyi.pay.model.vo.AliPayCallBackVO;
import com.ruoyi.pay.model.vo.PayResultEvenVO;
import com.ruoyi.pay.service.PayFinishService;
import com.ruoyi.pay.service.PayService;
import com.ruoyi.pay.util.URLEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 支付宝支付 service-impl
 *
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:38
 */
@Slf4j
@Service(value = "aliPayServiceImpl")
@PayServiceType(payWay = {PayWay.ALI_APP, PayWay.ALI_PC, PayWay.ALI_WAP})
public class AliPayServiceImpl implements PayService {

    @Value("${ali.pay.appid}")
    private String appid;
    @Value("${ali.pay.privateKey}")
    private String privateKey;
    @Value("${ali.pay.publicKey}")
    private String publicKey;
    @Value("${ali.pay.inputCharset}")
    private String inputCharset;
    @Value("${ali.pay.signType}")
    private String signType;
    @Value("${ali.pay.gateway.url}")
    private String gatewayUrl;

    @Value("${callBack.notifyUrl}")
    private String notifyUrl;
    @Value("${callBack.returnUrl}")
    private String returnUrl;


    @Autowired
    private PayFinishService payFinishService;

    private AlipayClient getAlipayClient() {
		/*// 证书方式
		String path = AlipayUtils.class.getResource("/").getPath();
		// 加载证书
		CertAlipayRequest certRequest = new CertAlipayRequest();
		certRequest.setServerUrl(gatewayUrl);
		certRequest.setFormat("json");
		certRequest.setAppId(appid);
		certRequest.setPrivateKey(privateKey);
		certRequest.setSignType("RSA2");

		certRequest.setCertPath(path + "ali/appCertPublicKey_2021002195629472.crt");

		certRequest.setAlipayPublicCertPath(path + "ali/alipayCertPublicKey_RSA2.crt");

		certRequest.setRootCertPath(path + "ali/alipayRootCert.crt");
		DefaultAlipayClient defaultAlipayClient = null;
		try {
			defaultAlipayClient = new DefaultAlipayClient(certRequest);
		} catch (AlipayApiException e) {
			if(log.isErrorEnabled()) {
				log.error("支付宝获取连接失败",e);
			}
			throw  new RuntimeException(RespEnum.ALI_TRANSFER_ERROR.getDesc(),e);
		}
		return defaultAlipayClient;*/
        //无需证书操作
        return new DefaultAlipayClient(gatewayUrl, appid,
                privateKey, "json", inputCharset, publicKey
                , signType);

    }

    /**
     * 创建预支付
     *
     * @param payVO
     * @return
     */
    @Override
    public ResPayResultVO createBalance(ReqPayVO payVO) {
        try {
            ResPayResultVO prePayResult = new ResPayResultVO();
            AlipayRequest request = null;

            switch (payVO.getPayWay()) {
                case ALI_APP: {
                    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                    //8分钟过期
                    model.setTimeoutExpress("8m");
                    model.setBody("body");
                    model.setSubject(payVO.getSubject());
                    model.setTotalAmount(payVO.getPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    model.setOutTradeNo(payVO.getRunningNum());
                    model.setTimeoutExpress("30m");
                    model.setProductCode("QUICK_MSECURITY_PAY");
                    //可携带数据
//                    model.setPassbackParams(URLEncoderUtil.getURLEncoderString(payVO.getOrderId()));
                    model.setPassbackParams(URLEncoderUtil.getURLEncoderString(JSONObject.toJSONString(new ReqPayVO(payVO.getRunningNum(),payVO.getOrderId(),payVO.getType()))));

                    request = new AlipayTradeAppPayRequest();
//                    支付成功后前端跳转URL
                    request.setReturnUrl(getReturnUrl(payVO));
//                    设置后端回调地址
                    request.setNotifyUrl(getNotifyUrl(payVO));
                    request.setBizModel(model);
                    log.info("支付宝入参：{}", JSONObject.toJSONString(request));
                    prePayResult.setAliAppResult(getAlipayClient().sdkExecute(request).getBody());
                    break;
                }
                case ALI_PC: {
                    AlipayTradePagePayModel model = new AlipayTradePagePayModel();
                    model.setBody("body");
                    model.setSubject(payVO.getSubject());
                    model.setTotalAmount(payVO.getPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    model.setOutTradeNo(payVO.getRunningNum());
                    model.setTimeoutExpress("30m");
                    model.setProductCode("FAST_INSTANT_TRADE_PAY");
                    //可携带数据
//                    model.setPassbackParams(payVO.getOrderId());
                    model.setPassbackParams(URLEncoderUtil.getURLEncoderString(JSONObject.toJSONString(new ReqPayVO(payVO.getRunningNum(),payVO.getOrderId(),payVO.getType()))));


                    request = new AlipayTradeAppPayRequest();
//                    支付成功后前端跳转URL
                    request.setReturnUrl(getReturnUrl(payVO));
//                    设置后端回调地址
                    request.setNotifyUrl(getNotifyUrl(payVO));
                    request.setBizModel(model);
                    log.info("支付宝入参：{}", JSONObject.toJSONString(request));
                    prePayResult.setAliPcResult(getAlipayClient().pageExecute(request).getBody());
                    break;
                }
                case ALI_WAP: {
                    AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
                    model.setBody("body");
                    model.setSubject(payVO.getSubject());
                    model.setTotalAmount(payVO.getPayMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    model.setOutTradeNo(payVO.getRunningNum());
                    model.setTimeoutExpress("30m");
                    model.setProductCode("QUICK_WAP_WAY");
                    //可携带数据
                    model.setPassbackParams(URLEncoderUtil.getURLEncoderString(JSONObject.toJSONString(new ReqPayVO(payVO.getRunningNum(),payVO.getOrderId(),payVO.getType()))));

                    request = new AlipayTradeAppPayRequest();
//                    支付成功后前端跳转URL
                    request.setReturnUrl(getReturnUrl(payVO));
//                    设置后端回调地址
                    request.setNotifyUrl(getNotifyUrl(payVO));
                    request.setBizModel(model);


                    log.info("支付宝入参：{}", JSONObject.toJSONString(request));
                    prePayResult.setAliWapResult(getAlipayClient().pageExecute(request).getBody());
                    break;
                }
            }
            // 预支付成功
            payFinishService.advancePaySuccess(payVO);
            return prePayResult;
        } catch (AlipayApiException e) {
            log.error("支付宝 支付异常", e);
            throw new RuntimeException(RespEnum.ALI_PAY_ERROR.getDesc(), e);
        }
    }

    /**
     * 获取支付成功后前端路由地址
     * @param payVO
     * @return
     */
    private String getReturnUrl(ReqPayVO payVO) {
        return String.format(returnUrl, payVO.getPayWay().getValue());
    }

    /**
     * 获取异步回调地址
     * @param payVO
     * @return
     */
    private String getNotifyUrl(ReqPayVO payVO) {
        return String.format(notifyUrl, payVO.getPayWay().getValue());
    }

    /**
     * 退款
     *
     * @param refundVO
     * @return
     */
    @Override
    public ResRefundResultVO refundBalance(ReqRefundVO refundVO) {
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        //与预授权转支付商户订单号相同，代表对该笔交易退款
        model.setOutTradeNo(refundVO.getRunningNum());
        model.setRefundAmount(refundVO.getRefundAmount().toString());
        model.setRefundReason("退款" + System.currentTimeMillis());
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
        model.setOutRequestNo(refundVO.getOutRefundNo());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        try {
            AlipayTradeRefundResponse response = getAlipayClient().execute(request);
            String code = response.getCode();
            if (!"10000".equals(code)) {
                new RuntimeException(RespEnum.ALI_REFUND_ERROR.getDesc());
            }
            if (log.isDebugEnabled()) {
                log.debug("支付宝响应数据{}", JSON.toJSONString(response));
            }
            return new ResRefundResultVO().
                    setIdOrderInfo(refundVO.getOrderInfoId()).
                    setOutRefundNo(refundVO.getOutRefundNo()).
                    setPayWay(refundVO.getPayWay()).
                    setRunningNum(refundVO.getRunningNum())
                    .setRefundTotal(refundVO.getRefundAmount());
        } catch (AlipayApiException e) {
            if (log.isErrorEnabled()) {
                log.error("支付宝退款失败", e);
            }
            throw new RuntimeException(RespEnum.ALI_REFUND_ERROR.getDesc(), e);
        }
    }

    /**
     * 转账
     *
     * @param reqTransferAccountsVO :
     * @return : com.ruoyi.pay.model.res.ResTransferAccountsVO
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:19
     */
    @Override
    public ResTransferAccountsVO transferAccounts(ReqTransferAccountsVO reqTransferAccountsVO) {
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo(reqTransferAccountsVO.getRunningNum());
        model.setTransAmount(reqTransferAccountsVO.getTransAmount().setScale(2, RoundingMode.HALF_UP).toString());
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setBizScene("DIRECT_TRANSFER");
        model.setOrderTitle(reqTransferAccountsVO.getTaskTitle());
        Participant participant = new Participant();
        participant.setName(reqTransferAccountsVO.getName());
        participant.setIdentityType("ALIPAY_USER_ID");
        participant.setIdentity(reqTransferAccountsVO.getIdentity());
        model.setPayeeInfo(participant);
        model.setRemark(reqTransferAccountsVO.getTransferRemark());
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizModel(model);
        try {
            AlipayFundTransUniTransferResponse response = getAlipayClient().certificateExecute(request);
            if (log.isDebugEnabled()) {
                log.info("支付宝响应数据{}", JSON.toJSONString(response));
            }
            ResTransferAccountsVO resTransferAccountsVO = new ResTransferAccountsVO()
                    .setPayWay(reqTransferAccountsVO.getPayWay())
                    .setOutBizNo(reqTransferAccountsVO.getRunningNum())
                    .setOrderId(response.getOrderId())
                    .setPayFundOrderId(response.getPayFundOrderId())
                    .setTransDate(response.getTransDate())
                    .setServiceChargeMoney(reqTransferAccountsVO.getServiceChargeMoney())
                    .setTransAmount(reqTransferAccountsVO.getTransAmount())
                    .setReturnValueJson(JSON.toJSONString(response));
            if ("10000".equals(response.getCode()) && "SUCCESS".equals(response.getStatus())) {
                // 转账成功
                resTransferAccountsVO.setIsSuccess(true);
            } else {
                // 转账失败
                resTransferAccountsVO.setIsSuccess(false);
            }
            return resTransferAccountsVO;
        } catch (AlipayApiException e) {
            if (log.isErrorEnabled()) {
                log.error("支付宝转账失败", e);
            }
            throw new RuntimeException(RespEnum.ALI_TRANSFER_ERROR.getDesc(), e);
        }
    }

    /**
     * 异步回调
     *
     * @param request
     * @return
     */
    @Override
    public String payNotifyCallBack(HttpServletRequest request, PayWay payWay) {
        String result = null;
//        支付宝回调
        log.info("支付宝回调。。。。。。。");
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            AliPayCallBackVO aliPayCallBackVo = buildAliPayNotifyParam(params);

            log.info("支付宝报回调参数：{}", JSONObject.toJSONString(aliPayCallBackVo));

            boolean verifyResult = verifyingSignature(params);
            log.info("验证签名：{}", verifyResult);
            if (verifyResult) {
                String passbackParams = aliPayCallBackVo.getPassback_params();
                payFinishService.payNotifyCallBackFinish(passbackParams, new PayResultEvenVO().setPayWay(payWay).setAliPayCallBackVo(aliPayCallBackVo));
                result = "success";
                //推送事件
            } else {
                result = "failure";
            }

        } catch (Exception e) {
            log.error("验签失败！", e);
            result = "failure";
        }
        return result;
    }



    /**
     * 验证签名并返回订单号 失败则返回null
     *
     * @param request
     */
    @Override
    public String verifyingSignatureReturnOrderNumber(HttpServletRequest request, PayWay payWay) {
        String result = null;
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            boolean verifyResult = verifyingSignature(params);
            if (verifyResult) {
                AliPayCallBackVO aliPayCallBackVo = buildAliPayNotifyParam(params);
                result = aliPayCallBackVo.getOut_trade_no();
                return result;
            } else {
                return result;
            }
        } catch (AlipayApiException e) {
            log.error("验签失败！", e);
            return result;

        }
    }


    /**
     * 验证签名
     *
     * @param params
     * @return
     * @throws AlipayApiException
     */
    private boolean verifyingSignature(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, publicKey, inputCharset,
                signType);
    }

    /**
     * 将request中的参数转换成Map
     *
     * @param request
     * @return
     */
    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    private AliPayCallBackVO buildAliPayNotifyParam(Map<String, String> params) {
        String json = JSON.toJSONString(params);
        return JSON.parseObject(json, AliPayCallBackVO.class);
    }


}
