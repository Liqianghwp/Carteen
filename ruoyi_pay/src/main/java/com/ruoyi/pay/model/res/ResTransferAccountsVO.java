package com.ruoyi.pay.model.res;

import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 转账返回值
 * @author : Lin
 * @version : v1.0
 * @createTime : [2021/11/26 0026 13:27]
 */
@Accessors(chain = true)
@Data
public class ResTransferAccountsVO {

    @ApiModelProperty(value="是否成功")
    private Boolean isSuccess;

    @ApiModelProperty(value="返回值json")
    private String returnValueJson;

    @ApiModelProperty(value="支付通道途径")
    private PayWay payWay;

    @ApiModelProperty(value="商户订单号")
    private String outBizNo;

    @ApiModelProperty(value="转账订单号")
    private String orderId;

    @ApiModelProperty(value="支付资金流水号")
    private String payFundOrderId;

    @ApiModelProperty(value="订单支付时间")
    private String transDate;

    @ApiModelProperty(value="手续费")
    private BigDecimal serviceChargeMoney;

    @ApiModelProperty(value="转账金额")
    private BigDecimal transAmount;
}
