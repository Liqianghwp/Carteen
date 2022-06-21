package com.ruoyi.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.ruoyi.pay.model.common.PayServiceType;
import com.ruoyi.pay.model.enums.PayWay;
import com.ruoyi.pay.model.enums.RespEnum;
import com.ruoyi.pay.model.req.ReqPayVO;
import com.ruoyi.pay.model.req.ReqRefundVO;
import com.ruoyi.pay.model.req.ReqTransferAccountsVO;
import com.ruoyi.pay.model.res.ResPayResultVO;
import com.ruoyi.pay.model.res.ResRefundResultVO;
import com.ruoyi.pay.model.res.ResTransferAccountsVO;
import com.ruoyi.pay.model.vo.UnionPayCallBackVO;
import com.ruoyi.pay.service.PayService;
import com.ruoyi.pay.service.impl.union.sdk.AcpService;
import com.ruoyi.pay.service.impl.union.sdk.SDKConfig;
import com.ruoyi.pay.service.impl.union.sdk.SDKConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname UnionPayServiceImpl
 * @Description 云闪付/银联支付
 * @Date 2022/5/19 19:09
 * @Created by YuLiu
 */

@Slf4j
@Service(value = "unionPayServiceImpl")
@PayServiceType(payWay = {
        PayWay.UNION_PAY_APP,
        PayWay.UNION_PAY_WEB,
        PayWay.CLOUD_QUICK_PASS_WEB,
        PayWay.CLOUD_QUICK_PASS_WAP,
})
public class UnionPayServiceImpl implements PayService {

//    @Resource
//    private PayFinishService payFinishService;


    //    商户号
    @Value("${unionpay.signCert.qrMerId}")
    private String merId;
//    @Value("${app.msgType_refund}")
//    private String msgType_refund;

    /**
     * 商品购买的回调接口
     */
    @Value("${callBack.notifyUrl}")
    private String notifyUrl;
    /**
     * 商家入驻的回调接口
     */


    @Value("${unionpay.frontUrl.app}")
    private String appFrontUrl;
    @Value("${unionpay.frontUrl.web}")
    private String webFrontUrl;

    /**
     * 创建预支付
     *
     * @param payVO :
     * @return
     */
    @Override
    public ResPayResultVO createBalance(ReqPayVO payVO) {

        try {
            log.info("预支付");

            ResPayResultVO resPayResultVO = new ResPayResultVO();
            PayWay payWay = payVO.getPayWay();
            Map<String, String> requestData = new HashMap<String, String>();


            /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
//            requestData.put("version", SDKConstants.VERSION_6_0_0);              //版本号，全渠道默认值
            requestData.put("version", SDKConstants.VERSION_5_1_0);              //版本号，全渠道默认值
            requestData.put("encoding", SDKConstants.UTF_8_ENCODING);              //字符集编码，可以使用UTF-8,GBK两种方式
            requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
            requestData.put("txnType", "01");                          //交易类型 ，01：消费
            requestData.put("txnSubType", "01");                          //交易子类型， 01：自助消费


            /***商户接入参数***/
            requestData.put("merId", merId);                              //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
            requestData.put("accessType", "0");                          //接入类型，0：直连商户
            requestData.put("orderId", payVO.getOrderId());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
            requestData.put("txnTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));        //订单发送时间，取系统时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
            requestData.put("currencyCode", "156");                      //交易币种（境内商户一般是156 人民币）
//            requestData.put("txnAmt", payVO.getPayMoney().multiply(new BigDecimal(100)).toString());                              //交易金额，单位分，不要带小数点
            requestData.put("txnAmt", String.valueOf(payVO.getPayMoney().multiply(new BigDecimal(100)).intValue()));                              //交易金额，单位分，不要带小数点
            //requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

            requestData.put("riskRateInfo", "{commodityName=" + payVO.getSubject() + "}");

            //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
            //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
            //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知

//            if ("0".equals(payVO.getType())) {
//                requestData.put("frontUrl", notifyUrl);
//            }else {
//                requestData.put("frontUrl", notifyUrlNo);
//            }

            //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
            //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
            //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
            //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
            //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
//            requestData.put("backUrl", returnUrl);
            requestData.put("backUrl", notifyUrl);

            // 订单超时时间。
            // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
            // 此时间建议取支付时的北京时间加15分钟。
            // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
            requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 8 * 60 * 1000));
            //////////////////////////////////////////////////
            //
            //       报文中特殊用法请查看 special_use_purchase.txt
            //
            //////////////////////////////////////////////////
            Map<String, String> submitFromData = new HashMap<>();

            String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
            String appRequestUrl = SDKConfig.getConfig().getAppRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.appTransUrl
            String html = null;


            switch (payWay) {
                case UNION_PAY_APP:
//                    银联APP支付
                    requestData.put("frontUrl", appFrontUrl);

                    /** 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
                    submitFromData = AcpService.sign(requestData, SDKConstants.UTF_8_ENCODING);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

                    html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, SDKConstants.UTF_8_ENCODING);   //生成自动跳转的Html表单
                    break;
                case CLOUD_QUICK_PASS_WAP:
//                    云闪付APP支付
                    requestData.put("bizType", "000201");                      //业务类型，B2C网关支付，手机wap支付
                    requestData.put("channelType", "08");                      //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
                    requestData.put("frontUrl", appFrontUrl);

                    /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
                    submitFromData = AcpService.sign(requestData, SDKConstants.UTF_8_ENCODING);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

                    html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, SDKConstants.UTF_8_ENCODING);   //生成自动跳转的Html表单
                    resPayResultVO.setUnionAppWapResult(html);
                    break;
                case UNION_PAY_WEB:
//                    银联WEB支付
                    requestData.put("frontUrl", webFrontUrl);

                    /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
                    submitFromData = AcpService.sign(requestData, SDKConstants.UTF_8_ENCODING);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
                    html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, SDKConstants.UTF_8_ENCODING);   //生成自动跳转的Html表单

                    break;
                case CLOUD_QUICK_PASS_WEB:
//                    云闪付WEB支付
                    requestData.put("frontUrl", webFrontUrl);

                    requestData.put("bizType", "000000");                      //业务类型，B2C网关支付，手机wap支付
                    requestData.put("channelType", "07");                      //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机


                    /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
                    submitFromData = AcpService.sign(requestData, SDKConstants.UTF_8_ENCODING);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

                    html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, SDKConstants.UTF_8_ENCODING);   //生成自动跳转的Html表单

                    //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
//            resp.getWriter().write(html);
//
                    resPayResultVO.setUnionPayWapResult(html);
                    break;
                default:
                    break;


            }
            log.info("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
            return resPayResultVO;

        } catch (Exception e) {
            log.error("云闪付 支付异常", e);
            throw new RuntimeException(RespEnum.UNIONPAY_PAY_ERROR.getDesc(), e);
        }
    }

    /**
     * 退款方法
     *
     * @param refundVO :
     * @return
     */
    @Override
    public ResRefundResultVO refundBalance(ReqRefundVO refundVO) {
        log.info("银联/云闪付退款开始");
        Map<String, String> reqData = new TreeMap<String, String>();


        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        reqData.put("version", SDKConstants.VERSION_6_0_0);           //版本号
        reqData.put("encoding", SDKConstants.UTF_8_ENCODING);      //字符集编码

        reqData.put("signMethod", SDKConfig.getConfig().getSignMethod());         //签名方法
        //交易类型 31-消费撤销
        reqData.put("txnType", "31");
        //交易子类型  默认00
        reqData.put("txnSubType", "00");
        //业务类型
        reqData.put("bizType", "000201");
        //渠道类型，07-PC，08-手机
        reqData.put("channelType", "07");


        /***商户接入参数***/
        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        reqData.put("merId", merId);
        //接入类型，商户接入固定填0，不需修改
        reqData.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        reqData.put("orderId", refundVO.getOrderInfoId());
        //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        reqData.put("txnTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        //【撤销金额】，消费撤销时必须和原消费金额相同
        reqData.put("txnAmt", refundVO.getRefundAmount().multiply(new BigDecimal(100)).toString());
        //交易币种(境内商户一般是156 人民币)
        reqData.put("currencyCode", "156");

        reqData.put("backUrl", "http://www.specialUrl.com");
        reqData.put("origQryId", refundVO.getOutRefundNo());


        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文**/

        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        Map<String, String> signData = AcpService.sign(reqData, SDKConstants.UTF_8_ENCODING);


        //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl

        String url = SDKConfig.getConfig().getBackRequestUrl();

        //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，

        //调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

        Map<String, String> rspData = AcpService.post(signData, url, SDKConstants.UTF_8_ENCODING);

        ResRefundResultVO resRefundResultVO = new ResRefundResultVO();

        resRefundResultVO.setIdOrderInfo(refundVO.getOrderInfoId());
        resRefundResultVO.setPayWay(refundVO.getPayWay());
        resRefundResultVO.setRefundTotal(refundVO.getRefundAmount());
        resRefundResultVO.setRunningNum(rspData.get("queryId"));
        resRefundResultVO.setOutRefundNo(rspData.get("origQryId"));
        return resRefundResultVO;
    }

    /**
     * 转账
     *
     * @param reqTransferAccountsVO :
     * @return : com.ruoyi.pay.model.res.ResTransferAccountsVO
     * @DATE 2022/1/21 0021 16:19
     */
    @Override
    public ResTransferAccountsVO transferAccounts(ReqTransferAccountsVO reqTransferAccountsVO) {
        return null;
    }

    /**
     * 异步回调
     *
     * @param request
     * @param payWay
     * @return
     */
    @Override
    public String payNotifyCallBack(HttpServletRequest request, PayWay payWay) {
        String result = null;
        Map<String, String> params = convertRequestParamsToMap(request);
        UnionPayCallBackVO unionPayCallBackVO = buildUnionPayNotifyParam(params);

        boolean verifyResult = verifyingSignature(params);

        if (verifyResult) {
            String passbackParams = unionPayCallBackVO.getReqReserved1();
//            payFinishService.payNotifyCallBackFinish(passbackParams, new PayResultEvenVO().setPayWay(payWay).setUnionPayCallBackVO(unionPayCallBackVO));
            result = "success";
            //推送事件
        } else {
            result = "failure";
        }

        return result;
    }

    /**
     * 验证签名
     *
     * @param request
     * @param payWay
     * @return
     */
    @Override
    public String verifyingSignatureReturnOrderNumber(HttpServletRequest request, PayWay payWay) {
        return null;
    }


    /**
     * 将request中的参数转换成Map
     *
     * @param request
     * @return
     */
    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
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

    /**
     * 转换成银联支付返回实体
     *
     * @param params
     * @return
     */
    private UnionPayCallBackVO buildUnionPayNotifyParam(Map<String, String> params) {
        String json = JSON.toJSONString(params);
        return JSON.parseObject(json, UnionPayCallBackVO.class);
    }


    /**
     * 验证签名
     *
     * @param params
     * @return
     * @throws AlipayApiException
     */
    private boolean verifyingSignature(Map<String, String> params) {
        return AcpService.validate(params, SDKConstants.UTF_8_ENCODING);
    }

}
