package com.ruoyi.pay.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付返回的信息
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:28
 */
@Accessors(chain = true)
@Data
@ApiModel(value = "支付返回的信息")
public class ResPayResultVO {

    @ApiModelProperty(value = "余额支付结果")
    private ResAccountPayResultVO accountPayResult;

    @ApiModelProperty(value = "微信App支付结果")
    private ResWechatPayResultVO  wechatAppPayResult;

    @ApiModelProperty(value = "微信公众号支付结果")
    private ResWechatPayResultVO wechatJsApiResult;

    @ApiModelProperty(value = "支付宝APP支付结果")
    private String aliAppResult;

    @ApiModelProperty(value = "支付宝PC支付结果")
    private String aliPcResult;

    @ApiModelProperty(value = "支付宝WAP支付结果")
    private String aliWapResult;

    @ApiModelProperty(value = "微信小程序支付结果")
    private ResWechatPayResultVO wechatAppletResult;

    @ApiModelProperty(value = "微信H5支付结果")
    private String wechatMwebResult;

    @ApiModelProperty(value = "微信Native支付结果 base64 图片")
    private String wechatNativeResult;

    @ApiModelProperty(value = "云闪付二维码支付结果 base64 图片")
    private String unionPayQrCodeResult;

    @ApiModelProperty(value = "云闪付WAP支付结果")
    private String unionPayWapResult;

    @ApiModelProperty(value = "云闪付APP支付结果")
    private String unionAppWapResult;

    @ApiModelProperty(value = "支付单号")
    private String payNo;
}
