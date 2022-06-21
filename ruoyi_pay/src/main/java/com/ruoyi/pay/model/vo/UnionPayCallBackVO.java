package com.ruoyi.pay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname UnionPayCallBackVO
 * @Description 银联支付返回结果
 * @Date 2022/5/20 17:36
 * @Created by YuLiu
 */
@ApiModel(value = "银联支付返回结果")
@Data
public class UnionPayCallBackVO implements Serializable {
    private static final long serialVersionUID = 2410647391474714824L;

    @ApiModelProperty(value = "应答码")
    private String respCode;
    @ApiModelProperty(value = "应答码描述")
    private String respMsg;
    @ApiModelProperty(value = "交易查询流水号")
    private String queryId;
    @ApiModelProperty(value = "系统跟踪号")
    private String sysTraNo;
    @ApiModelProperty(value = "系统交易时间")
    private String sysTm;
    @ApiModelProperty(value = "银联保留域1")
    private String reserved1;
    @ApiModelProperty(value = "银联保留域2")
    private String reserved2;
    @ApiModelProperty(value = "商户代码")
    private String merId;
    @ApiModelProperty(value = "商户订单号")
    private String orderId;
    @ApiModelProperty(value = "交易币种")
    private String currencyCode;
    @ApiModelProperty(value = "交易金额")
    private String txnAmt;
    @ApiModelProperty(value = "账号类型")
    private String accType;
    @ApiModelProperty(value = "账号")
    private String accNo;
    @ApiModelProperty(value = "姓名")
    private String customerNm;
    @ApiModelProperty(value = "产品类型")
    private String bizType;
    @ApiModelProperty(value = "证件类型")
    private String certifTp;
    @ApiModelProperty(value = "证件号码")
    private String certifId;
    @ApiModelProperty(value = "手机号码")
    private String phoneNo;
    @ApiModelProperty(value = "CVN2")
    private String cvn2;
    @ApiModelProperty(value = "有效期")
    private String expired;
    @ApiModelProperty(value = "开户行代码")
    private String issInsCode;
    @ApiModelProperty(value = "开户行名称")
    private String issInsName;
    @ApiModelProperty(value = "账单类型")
    private String billType;
    @ApiModelProperty(value = "账单号码")
    private String billNo;
    @ApiModelProperty(value = "附言")
    private String postscript;
    @ApiModelProperty(value = "请求方保留域1")
    private String reqReserved1;
    @ApiModelProperty(value = "请求方保留域2")
    private String reqReserved2;


}
