package com.ruoyi.pay.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 退款请求参数
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:26
 */
@Data
@ApiModel(value = "退款请求参数")
public class ReqRefundBalanceVO {

    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private Long orderInfoId;
}
