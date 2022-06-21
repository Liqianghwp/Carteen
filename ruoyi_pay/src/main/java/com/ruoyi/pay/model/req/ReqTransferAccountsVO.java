package com.ruoyi.pay.model.req;

import com.ruoyi.pay.model.enums.PayType;
import com.ruoyi.pay.model.enums.PayWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 转账请求参数
 * @author : Lin
 * @version : v1.0
 * @createTime : [2021/11/26 0026 13:30]
 */
@Accessors(chain = true)
@Data
public class ReqTransferAccountsVO {

    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private Long orderInfoId;

    @ApiModelProperty(value="任务标题")
    private String taskTitle;

    @ApiModelProperty(value = "转账备注")
    private String transferRemark;

    @ApiModelProperty(value="手续费")
    private BigDecimal serviceChargeMoney;

    @ApiModelProperty(value="转账金额")
    private BigDecimal transAmount;

    @ApiModelProperty(value="转账流水号",hidden = true)
    private String runningNum;

    @ApiModelProperty(value="支付通道途径")
    private PayWay payWay;

    @ApiModelProperty(value="转账支付类型")
    private PayType payType;

    @ApiModelProperty(value="是否需要校验")
    private Boolean isCheck = true;


    /** -------------------------- 支付宝 ------------------------------------- */
    @ApiModelProperty(value="参与方的标识 ID")
    private String identity;

    @ApiModelProperty(value="参与方真实姓名。如果非空，将校验收款支付宝账号姓名一致性。")
    private String name;
    /** -------------------------- 支付宝 ------------------------------------- */

    /** -------------------------- 微信 ------------------------------------- */
    @ApiModelProperty(value = "openId",hidden = true)
    private String openId;
    /** -------------------------- 微信 ------------------------------------- */
}
