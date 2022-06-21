package com.ruoyi.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxEntPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxEntPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.pay.model.common.PayServiceType;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.enums.RespEnum;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.req.ReqRefundVO;
import com.ruoyi.pay.model.req.ReqTransferAccountsVO;
import com.ruoyi.pay.model.res.ResPayResultVO;
import com.ruoyi.pay.model.res.ResRefundResultVO;
import com.ruoyi.pay.model.res.ResTransferAccountsVO;
import com.ruoyi.pay.model.res.ResWechatPayResultVO;
import com.ruoyi.pay.model.vo.PayResultEvenVO;
import com.ruoyi.pay.service.PayFinishService;
import com.ruoyi.pay.service.PayService;
import com.ruoyi.pay.util.QRCodeUtil;
import com.ruoyi.pay.util.URLEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName WechatPayService
 * @Description:
 * @Author: lilong12
 * @Date: 2020/3/14 0014
 **/
@Slf4j
@Service(value = "wechatPayServiceImpl")
@PayServiceType(payWay = {PayWay.WECHAT_APP, PayWay.WECHAT_APPLET, PayWay.WECHAT_JS_API, PayWay.WECHAT_H5, PayWay.WECHAT_NATIVE})
public class WechatPayServiceImpl implements PayService {

    @Value("${wechat.pay.key}")
    private String key;
    @Value("${wechat.pay.appletAppId}")
    private String appletAppId;
    @Value("${wechat.pay.appletMchId}")
    private String appletMchId;
    @Value("${wechat.pay.appId}")
    private String appId;
    @Value("${wechat.pay.appMchId}")
    private String appMchId;
    @Value("${wechat.pay.subscriptionAppId}")
    private String subscriptionAppId;
    @Value("${wechat.pay.subscriptionMchId}")
    private String subscriptionMchId;
    @Value("${wechat.pay.h5AppId}")
    private String h5AppId;
    @Value("${wechat.pay.h5MchId}")
    private String h5MchId;
    @Value("${wechat.pay.certName}")
    private String certName;

    @Value("${callBack.notifyUrl}")
    private String notifyUrl;

    @Autowired
    private PayFinishService payFinishService;

    /**
     * 创建预支付
     *
     * @param payVO
     * @return
     */
    @Override
    public ResPayResultVO createBalance(ReqPayVO payVO) {
        ResPayResultVO payResultVO = new ResPayResultVO();
        WxPayConfig wxPayConfig = new WxPayConfig();
//        payVO.setRunningNum(RunningNumberUtil.createRunningNumber());
        if (payVO.getUserIp() == null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            payVO.setUserIp(IpUtils.getIpAddr(request));
        }
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        switch (payVO.getPayWay()) {
            case WECHAT_APP: {
                wxPayConfig.setAppId(appId);
                wxPayConfig.setTradeType("APP");
                wxPayConfig.setMchKey(key);
                wxPayConfig.setMchId(appMchId);
//                设置回调url
                wxPayConfig.setNotifyUrl(String.format(notifyUrl, payVO.getPayWay().getValue()));

                request.setTradeType("APP");
                break;
            }
            case WECHAT_APPLET: {
                //与APP不相同
                wxPayConfig.setAppId(appletAppId);
                wxPayConfig.setTradeType("JSAPI");
                wxPayConfig.setMchKey(key);
                //与APP相同
                wxPayConfig.setMchId(appletMchId);
//                设置回调url
                wxPayConfig.setNotifyUrl(String.format(notifyUrl, payVO.getPayWay().getValue()));

                request.setOpenid(payVO.getOpenId());
                request.setTradeType(wxPayConfig.getTradeType());
                break;
            }
            case WECHAT_JS_API: {
                wxPayConfig.setAppId(subscriptionAppId);
                wxPayConfig.setTradeType("JSAPI");
                wxPayConfig.setMchId(subscriptionMchId);
                wxPayConfig.setMchKey(key);
//                设置回调路径
                wxPayConfig.setNotifyUrl(String.format(notifyUrl, payVO.getPayWay().getValue()));

                request.setDeviceInfo("WEB");
                request.setOpenid(payVO.getOpenId());
                request.setTradeType(wxPayConfig.getTradeType());
                break;
            }
            case WECHAT_H5: {
                wxPayConfig.setAppId(h5AppId);
                wxPayConfig.setTradeType("MWEB");
                wxPayConfig.setMchId(h5MchId);
                wxPayConfig.setMchKey(key);
//                设置回调路径
                wxPayConfig.setNotifyUrl(String.format(notifyUrl, payVO.getPayWay().getValue()));
                request.setDeviceInfo("WEB");
                request.setOpenid(payVO.getOpenId());
                Map<String, Map<String, String>> sceneInfo = new HashMap<>(0);
                Map<String, String> h5Info = new HashMap<>(0);
                h5Info.put("wap_url", "http://kds.gckerui.com");
                h5Info.put("wap_name", "刊大师");
                h5Info.put("type", "Wap");
                sceneInfo.put("h5_info", h5Info);
                request.setSceneInfo(JSON.toJSONString(sceneInfo));
                request.setTradeType(wxPayConfig.getTradeType());
                break;
            }
            case WECHAT_NATIVE: {
                wxPayConfig.setAppId(h5AppId);
                wxPayConfig.setTradeType("NATIVE");
                wxPayConfig.setMchKey(key);
                wxPayConfig.setMchId(subscriptionMchId);
//                设置回调路径
                wxPayConfig.setNotifyUrl(String.format(notifyUrl, payVO.getPayWay().getValue()));
                request.setTradeType(wxPayConfig.getTradeType());
                request.setProductId(payVO.getOrderId().toString());
            }
        }
        request.setNonceStr(String.valueOf(System.currentTimeMillis()));
        //商品描述
        request.setBody(payVO.getSubject());

//        可携带参数
        request.setAttach(URLEncoderUtil.getURLEncoderString(JSONObject.toJSONString(new ReqPayVO(payVO.getRunningNum(), payVO.getOrderId(), payVO.getType()))));

        request.setTotalFee(payVO.getPayMoney().multiply(new BigDecimal(100)).intValue());
        request.setOutTradeNo(payVO.getRunningNum());
//        request.setFeeType("CNY");
        request.setSpbillCreateIp(payVO.getUserIp());
        request.setNotifyURL(wxPayConfig.getNotifyUrl());
        //失效时间8分钟
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, 8);
        request.setTimeExpire(simpleDateFormat.format(now.getTime()));

        log.info("入参信息：{}", JSONObject.toJSONString(request));

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        try {
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(request);
            //订单支付异常
            if (wxPayUnifiedOrderResult == null) {
                throw new RuntimeException(RespEnum.PAY_ORDER_ERROR.getDesc());
            }
            //验证预支付接口返回值
            if (!"SUCCESS".equals(wxPayUnifiedOrderResult.getReturnCode())) {
                throw new RuntimeException(RespEnum.WEIXIN_PAY_ERROR.getDesc());
            }
            Map<String, String> configMap = new HashMap<>(0);
            Map<String, String> payInfo = new HashMap<>(0);
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = String.valueOf(System.currentTimeMillis());
            switch (payVO.getPayWay()) {
                case WECHAT_APP: {
                    configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
                    configMap.put("partnerid", appMchId);
                    String packageValue = "Sign=WXPay";
                    configMap.put("package", packageValue);
                    configMap.put("timestamp", timestamp);
                    configMap.put("noncestr", nonceStr);
                    configMap.put("appid", appId);
                    // 此map用于客户端与微信服务器交互
                    ResWechatPayResultVO result = new ResWechatPayResultVO();
                    result.setPaySign(SignUtils.createSign(configMap, wxPayConfig.getMchKey(), null));
                    result.setPrepayid(wxPayUnifiedOrderResult.getPrepayId());
                    result.setPartnerid(appMchId);
                    result.setAppid(appId);
                    result.setPack(packageValue);
                    result.setTimestamp(timestamp);
                    result.setNoncestr(nonceStr);
                    result.setSignType(WxPayConstants.SignType.MD5);
                    payResultVO.setWechatAppPayResult(result);
                    break;
                }
                case WECHAT_APPLET: {
                    payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
                    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    payInfo.put("timeStamp", timestamp);
                    payInfo.put("nonceStr", nonceStr);
                    payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                    payInfo.put("signType", WxPayConstants.SignType.MD5);

                    ResWechatPayResultVO result = new ResWechatPayResultVO();
                    result.setPaySign(SignUtils.createSign(payInfo, wxPayConfig.getMchKey(), null));
                    result.setPrepayid(wxPayUnifiedOrderResult.getPrepayId());
                    result.setAppid(wxPayUnifiedOrderResult.getAppid());
                    result.setPack("prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                    result.setTimestamp(timestamp);
                    result.setNoncestr(nonceStr);
                    result.setSignType(WxPayConstants.SignType.MD5);
                    payResultVO.setWechatAppletResult(result);
                    break;
                }
                case WECHAT_JS_API: {
                    payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
                    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    payInfo.put("timeStamp", timestamp);
                    payInfo.put("nonceStr", nonceStr);
                    payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                    payInfo.put("signType", WxPayConstants.SignType.MD5);

                    ResWechatPayResultVO result = new ResWechatPayResultVO();
                    result.setPaySign(SignUtils.createSign(payInfo, wxPayConfig.getMchKey(), null));
                    result.setPrepayid(wxPayUnifiedOrderResult.getPrepayId());
                    result.setAppid(wxPayUnifiedOrderResult.getAppid());
                    result.setPack("prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                    result.setTimestamp(timestamp);
                    result.setNoncestr(nonceStr);
                    result.setSignType(WxPayConstants.SignType.MD5);
                    payResultVO.setWechatJsApiResult(result);
                    break;
                }
                case WECHAT_H5: {
                    payResultVO.setWechatMwebResult(wxPayUnifiedOrderResult.getMwebUrl());
                    break;
                }
                case WECHAT_NATIVE: {
                    BufferedImage bufferedImage = QRCodeUtil.createImage(wxPayUnifiedOrderResult.getCodeURL());
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
                    ImageIO.write(bufferedImage, "png", imOut);
                    String base64 = "data:image/png;base64," + Base64.encodeBase64String(bs.toByteArray());
                    payResultVO.setWechatNativeResult(base64);
//                    设置支付单号
                    payResultVO.setPayNo(payVO.getOrderId());
                    break;
                }
            }
            // 预支付成功
            payFinishService.advancePaySuccess(payVO);
        } catch (WxPayException e) {
            log.error("微信支付异常", e);
            throw new RuntimeException(RespEnum.WEIXIN_PAY_EXCEPTION.getDesc(), e);
        } catch (Exception e) {
            log.error("微信支付异常", e);
            throw new RuntimeException(RespEnum.WEIXIN_PAY_EXCEPTION.getDesc(), e);
        }
        return payResultVO;
    }

    /**
     * 退款
     *
     * @param refundVO
     * @return
     */
    @Override
    public ResRefundResultVO refundBalance(ReqRefundVO refundVO) {
        WxPayConfig wxPayConfig = new WxPayConfig();
        WxPayRefundRequest request = new WxPayRefundRequest();
        switch (refundVO.getPayWay()) {
            case WECHAT_APP:
                wxPayConfig.setAppId(appId);
                wxPayConfig.setMchKey(key);
                wxPayConfig.setMchId(appMchId);
                break;
            case WECHAT_APPLET:
                wxPayConfig.setAppId(appletAppId);
                wxPayConfig.setMchKey(key);
                wxPayConfig.setMchId(appletMchId);
                break;
            case WECHAT_JS_API:
                wxPayConfig.setAppId(subscriptionAppId);
                wxPayConfig.setMchId(subscriptionMchId);
                wxPayConfig.setMchKey(key);
                break;
            case WECHAT_H5:
                wxPayConfig.setAppId(h5AppId);
                wxPayConfig.setMchId(h5MchId);
                wxPayConfig.setMchKey(key);
                break;
            case WECHAT_NATIVE:
                wxPayConfig.setAppId(h5AppId);
                wxPayConfig.setMchId(subscriptionMchId);
                wxPayConfig.setMchKey(key);
                break;
        }
        SSLContext sslContext = null;
        try {
            sslContext = getSSLContext(wxPayConfig.getMchId());
        } catch (Exception e) {
            log.error("加载微信证书失败！", e);
            throw new RuntimeException(RespEnum.WECHAT_CERT_ERROR.getDesc());
        }
        wxPayConfig.setSslContext(sslContext);
        request.setOutRefundNo(refundVO.getOutRefundNo());
        request.setOutTradeNo(refundVO.getRunningNum());
        request.setTotalFee(refundVO.getRefundTotal().multiply(new BigDecimal(100)).intValue());
        request.setRefundFee(refundVO.getRefundAmount().multiply(new BigDecimal(100)).intValue());
        request.setNonceStr(String.valueOf(System.currentTimeMillis()));
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        try {
            WxPayRefundResult payRefundResult = wxPayService.refund(request);
            if (payRefundResult == null) {
                throw new RuntimeException(RespEnum.WECHAT_REFUND_ERROR.getDesc());
            }
            //验证退款接口返回值
            if ((payRefundResult.getResultCode().toUpperCase()).indexOf("SUCCESS") == -1) {
                throw new RuntimeException(RespEnum.WECHAT_REFUND_ERROR.getDesc());
            }
            if (log.isInfoEnabled()) {
                log.debug("微信响应数据{}", JSON.toJSONString(payRefundResult));
            }
            return new ResRefundResultVO().
                    setIdOrderInfo(refundVO.getOrderInfoId()).
                    setOutRefundNo(refundVO.getOutRefundNo()).
                    setPayWay(refundVO.getPayWay()).
                    setRunningNum(refundVO.getRunningNum())
                    .setRefundTotal(refundVO.getRefundAmount());

        } catch (WxPayException e) {
            throw new RuntimeException(RespEnum.WECHAT_REFUND_ERROR.getDesc());
        }
    }

    /**
     * 转账
     *
     * @param reqTransferAccountsVO :
     * @return : com.ruoyi.pay.model.res.ResTransferAccountsVO
     * @Author LinGQ
     * @DATE 2022/1/21 0021 16:27
     */
    @Override
    public ResTransferAccountsVO transferAccounts(ReqTransferAccountsVO reqTransferAccountsVO) {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(appletAppId);
        wxPayConfig.setMchId(appletMchId);
        wxPayConfig.setMchKey(key);
        SSLContext sslContext = null;
        try {
            sslContext = getSSLContext(wxPayConfig.getMchId());
        } catch (Exception e) {
            log.error("加载微信证书失败！", e);
            throw new RuntimeException(RespEnum.WECHAT_CERT_ERROR.getDesc());
        }
        wxPayConfig.setSslContext(sslContext);

        WxEntPayRequest request = new WxEntPayRequest();
        request.setPartnerTradeNo(reqTransferAccountsVO.getRunningNum());
        request.setOpenid(reqTransferAccountsVO.getOpenId());
        request.setCheckName("NO_CHECK");
        request.setAmount(reqTransferAccountsVO.getTransAmount().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue());
        request.setAmount(BigDecimal.valueOf(0.1).setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue());

        request.setDescription(reqTransferAccountsVO.getTransferRemark());
        request.setNonceStr(String.valueOf(System.currentTimeMillis()));
        request.setSpbillCreateIp(IpUtils.getIpAddr(ServletUtils.getRequest()));

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);

        try {
            WxEntPayResult wxEntPayResult = wxPayService.entPay(request);
            if (Objects.isNull(wxEntPayResult)) {
                throw new RuntimeException(RespEnum.WECHAT_TRANSFER_ERROR.getDesc());
            }
            if (log.isInfoEnabled()) {
                log.debug("微信响应数据{}", JSON.toJSONString(wxEntPayResult));
            }
            ResTransferAccountsVO resTransferAccountsVO = new ResTransferAccountsVO()
                    .setPayWay(PayWay.WECHAT_APPLET)
                    .setOutBizNo(reqTransferAccountsVO.getRunningNum())
                    .setOrderId(wxEntPayResult.getPaymentNo())
                    .setTransDate(wxEntPayResult.getPaymentTime())
                    .setTransAmount(reqTransferAccountsVO.getTransAmount())
                    .setReturnValueJson(JSON.toJSONString(wxEntPayResult));
            if ((wxEntPayResult.getReturnCode().toUpperCase()).indexOf("SUCCESS") == -1 ||
                    (wxEntPayResult.getResultCode().toUpperCase()).indexOf("SUCCESS") == -1) {
                // 转账失败
                resTransferAccountsVO.setIsSuccess(false);
            } else {
                // 转账成功
                resTransferAccountsVO.setIsSuccess(true);
            }
            return resTransferAccountsVO;
        } catch (WxPayException e) {
            log.error("微信转账异常", e);
            throw new RuntimeException(RespEnum.WECHAT_TRANSFER_ERROR.getDesc());
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
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            if (log.isInfoEnabled()) {
                log.debug("{}通知请求数据:reqStr={}", new Object[]{"【微信支付回调通知】", xmlResult});
            }
            WxPayService wxPayService = new WxPayServiceImpl();
            WxPayConfig wxPayConfig = new WxPayConfig();
            switch (payWay) {
                case WECHAT_APP:
                    wxPayConfig.setAppId(appId);
                    wxPayConfig.setTradeType("APP");
                    wxPayConfig.setMchKey(key);
                    wxPayConfig.setMchId(appMchId);
                    break;
                case WECHAT_APPLET:
                    //与APP不相同
                    wxPayConfig.setAppId(appletAppId);
                    wxPayConfig.setTradeType("JSAPI");
                    wxPayConfig.setMchKey(key);
                    //与APP相同
                    wxPayConfig.setMchId(appletMchId);
                    break;
                case WECHAT_JS_API:
                    wxPayConfig.setAppId(subscriptionAppId);
                    wxPayConfig.setTradeType("JSAPI");
                    wxPayConfig.setMchId(subscriptionMchId);
                    wxPayConfig.setMchKey(key);
                    break;
                case WECHAT_H5:
                    wxPayConfig.setAppId(h5AppId);
                    wxPayConfig.setTradeType("MWEB");
                    wxPayConfig.setMchId(h5MchId);
                    wxPayConfig.setMchKey(key);
                    break;
                case WECHAT_NATIVE:
                    wxPayConfig.setAppId(h5AppId);
                    wxPayConfig.setTradeType("NATIVE");
                    wxPayConfig.setMchKey(key);
                    wxPayConfig.setMchId(subscriptionMchId);
                    break;
            }
            wxPayService.setConfig(wxPayConfig);
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            if ("SUCCESS".equalsIgnoreCase(result.getResultCode()) && "SUCCESS".equalsIgnoreCase(result.getReturnCode())) {
                log.debug("-----------------------------------------------start 写入业务逻辑------------------------------------------");
                String attach = URLEncoderUtil.getURLDecoderString(result.getAttach());
                payFinishService.payNotifyCallBackFinish(attach, new PayResultEvenVO().setPayWay(payWay).setWxPayOrderNotifyResult(result));
                log.debug("-----------------------------------------------end 写入业务逻辑------------------------------------------");
                return resultXml(true, "OK");
            }
            log.debug(JSON.toJSONString(result));
            return resultXml(false, "签名异常");
        } catch (IOException e) {
            log.error("参数异常", e);
            return resultXml(false, "参数异常");
        } catch (WxPayException e) {
            log.error("解析异常", e);
            return resultXml(false, "解析异常");
        }
    }

    /**
     * 验证签名并返回订单号 失败则返回null
     *
     * @param request
     */
    @Override
    public String verifyingSignatureReturnOrderNumber(HttpServletRequest request, PayWay payWay) {
        return null;
    }


    private String resultXml(boolean flag, String desc) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        if (flag) {
            sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
        } else {
            sb.append("<return_code><![CDATA[FAIL]]></return_code>");
        }
        sb.append("<return_msg><![CDATA[" + desc + "]]></return_msg>");
        sb.append("</xml>");
        return sb.toString();
    }

    private SSLContext getSSLContext(String mchId) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //ClassPathResource cl = new ClassPathResource(certPath);
        String path = WechatPayServiceImpl.class.getClassLoader().getResource("cert").getPath();
        log.info("微信证书文件夹路径:" + path);
//        String wechatCertPath = path + File.separator + certName;
        String wechatCertPath = path + certName;
        log.info("微信证书路径:" + wechatCertPath);
        InputStream inputStream = new FileInputStream(new File(wechatCertPath));
        try {
            keyStore.load(inputStream, mchId.toCharArray());
        } finally {
            inputStream.close();
        }
        return SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
    }


}
