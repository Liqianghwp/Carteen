package com.ruoyi.pay.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信预支付结果
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:29
 */
@Data
@ApiModel("微信预支付结果")
public class ResWechatPayResultVO {

    @ApiModelProperty(value = "prepayid")
    private String prepayid;
    @ApiModelProperty(value = "partnerid")
    private String partnerid;
    @ApiModelProperty(value = "appid")
    private String appid;
    @ApiModelProperty(value = "package")
    private String pack;
    @ApiModelProperty(value = "timestamp")
    private String timestamp;
    @ApiModelProperty(value = "noncestr")
    private String noncestr;
    @ApiModelProperty(value = "paySign")
    private String paySign;
    @ApiModelProperty(value = "signType")
    private String signType;

}
