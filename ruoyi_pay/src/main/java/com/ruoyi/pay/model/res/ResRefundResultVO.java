package com.ruoyi.pay.model.res;

import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 退款返回的信息
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:28
 */
@Accessors(chain = true)
@Data
@ApiModel(value = "退款返回的信息")
public class ResRefundResultVO {

    @ApiModelProperty("订单id")
    private String idOrderInfo;

    @ApiModelProperty(value="支付通道途径")
    private PayWay payWay;

    @ApiModelProperty(value="退款金额")
    private BigDecimal refundTotal;

    @ApiModelProperty(value="支付流水号")
    private String runningNum;

    @ApiModelProperty(value = "退款流水号")
    private String outRefundNo;

}
