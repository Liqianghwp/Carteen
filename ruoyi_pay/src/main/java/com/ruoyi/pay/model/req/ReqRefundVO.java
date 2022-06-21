package com.ruoyi.pay.model.req;

import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 退款请求参数
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:27
 */
@Data
@ApiModel("退款请求参数")
public class ReqRefundVO {

    @NotNull(message = "支付通道不能为空")
    @ApiModelProperty(value="支付通道途径")
    private PayWay payWay;

    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private String orderInfoId;

    @ApiModelProperty(value = "退款金额总数")
    private BigDecimal refundTotal;

    @NotNull(message = "退款金额不能为空")
    @ApiModelProperty(value="退款金额")
    private BigDecimal refundAmount;

    //outTradeNo
    @ApiModelProperty(value="支付流水号")
    @NotBlank(message = "流水号不能为空")
    private String runningNum;

    @ApiModelProperty(value = "退款流水号")
    private String outRefundNo;

    @ApiModelProperty(value = "账户ID  余额退款时必填")
    private Long userAccountId;

}
