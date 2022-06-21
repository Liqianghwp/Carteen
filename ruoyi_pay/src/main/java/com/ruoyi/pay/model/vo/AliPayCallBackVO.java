package com.ruoyi.pay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("支付宝回调请求参数")
@Data
public class AliPayCallBackVO {
    @ApiModelProperty(value = "交易创建时间")
    private String gmt_create;

    @ApiModelProperty(value = "编码格式")
    private String charset;

    @ApiModelProperty(value = "")
    private String seller_email;

    @ApiModelProperty(value = "标题")
    private String subject;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "网关返回描述")
    private String msg;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "buyer_id")
    private String buyer_id;

    @ApiModelProperty(value = "发票金额")
    private String invoice_amount;

    @ApiModelProperty(value = "通知校验ID 返回SUCCESS该ID失效")
    private String notify_id;

    @ApiModelProperty(value = "单据业务列表")
    private String fund_bill_list;

    @ApiModelProperty(value = "通知类型")
    private String notify_type;

    @ApiModelProperty(value = "交易类型")
    private String trade_status;

    @ApiModelProperty(value = "实收金额")
    private String receipt_amount;

    @ApiModelProperty(value = "应用id")
    private String app_id;

    @ApiModelProperty(value = "买家支付金额")
    private String buyer_pay_amount;

    @ApiModelProperty(value = "签名类型")
    private String sign_type;

    @ApiModelProperty(value = "商户id")
    private String seller_id;

    @ApiModelProperty(value = "支付时间")
    private String gmt_payment;

    @ApiModelProperty(value = "通知时间")
    private String notify_time;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "订单编号")
    private String out_trade_no;

    @ApiModelProperty(value = "总金额")
    private String total_amount;

    @ApiModelProperty(value = "支付宝订单号")
    private String trade_no;

    @ApiModelProperty(value = "应用程序标识")
    private String auth_app_id;

    @ApiModelProperty(value = "买家登陆id")
    private String buyer_logon_id;

    @ApiModelProperty(value = "精度")
    private String point_amount;

    @ApiModelProperty(value = "预支付携带的参数")
    private String passback_params;
}
