package com.diandong.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname OrderPayVO
 * @Description 订单支付实体类
 * @Date 2022/6/16 10:36
 * @Created by YuLiu
 */
@ApiModel("订单支付实体类")
@Data
public class OrderPayVO implements Serializable {
    private static final long serialVersionUID = 1239847028466162468L;


    @ApiModelProperty(value = "订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payWay;

}
