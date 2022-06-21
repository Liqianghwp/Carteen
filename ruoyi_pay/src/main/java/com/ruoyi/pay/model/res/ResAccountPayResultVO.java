package com.ruoyi.pay.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 余额支付结果
 * @Author LinGQ
 * @DATE 2022/1/21 0021 15:27
 */
@Data
@ApiModel("余额支付结果")
@Accessors(chain = true)
public class ResAccountPayResultVO {

    @ApiModelProperty(value="支付流水号")
    private String payRunningNum;

    @ApiModelProperty(value="支付总金额")
    private BigDecimal orderPrice;
}
