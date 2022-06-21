package com.ruoyi.pay.model.req;

import com.ruoyi.pay.model.enums.BizTypeEnum;
import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 支付请求参数
 *
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:26
 */
@Data
@ApiModel("支付请求参数")
@NoArgsConstructor
@AllArgsConstructor
public class ReqPayVO {

    @ApiModelProperty(value = "用户id（余额支付用）")
    private Long userInfoId;

    @ApiModelProperty(value = "支付密码（余额支付用）")
    private String password;

    @ApiModelProperty(value = "支付流水号", hidden = true)
    private String runningNum;

    @ApiModelProperty(value = "用户ip")
    private String userIp;

    @NotNull(message = "支付通道不能为空")
    @ApiModelProperty(value = "支付通道途径")
    private PayWay payWay;

    @ApiModelProperty(value = "openId", hidden = true)
    private String openId;

    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单id不能为空")
    private String orderId;

    @ApiModelProperty(value = "订单金额")
    @NotNull(message = "订单金额不能为空")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "订单标题")
    private String subject;

    @ApiModelProperty(value = "类型：（0=菜品订单支付；1=充次；2=充钱）")
    private BizTypeEnum type;


    public ReqPayVO(String runningNum, String orderId, BizTypeEnum type) {
        this.runningNum = runningNum;
        this.orderId = orderId;
        this.type = type;
    }
}
