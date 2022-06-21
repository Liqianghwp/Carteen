package com.ruoyi.pay.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询订单是否支付 - 返回参数
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:27
 */
@Data
@ApiModel(value = "查询订单是否支付 - 返回参数")
public class ResOrderInfoVO {

    @ApiModelProperty("订单id")
    private Long idOrderInfo;

    @ApiModelProperty("是否支付")
    private Integer isPay;

}
