package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname AppRechargeVO
 * @Description APP充值&充次实体类
 * @Date 2022/6/17 16:37
 * @Created by YuLiu
 */
@Data
@ApiModel("APP充值&充次实体类")
public class AppRechargeVO implements Serializable {

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /**
     * 充值次数
     */
    @ApiModelProperty(value = "次数")
    private Integer times;
    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;


}
