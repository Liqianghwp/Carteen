package com.diandong.domain.vo;

import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 支付订单访问类
 */

@Data
@ApiModel("支付订单实体类")
public class PayOrderVO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8222327878683247737L;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private Long orderId;
    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;


}
