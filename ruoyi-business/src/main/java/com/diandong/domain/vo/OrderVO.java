package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 订单VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("订单VO实体类")
public class OrderVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂编号
     */
    @ApiModelProperty(value = "食堂编号")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    /**
     * 订单总价格
     */
    @ApiModelProperty(value = "订单总价格")
    private BigDecimal orderTotalPrice;

    /**
     * 菜品总数量
     */
    @ApiModelProperty(value = "菜品总数量")
    private Integer dishesTotalCount;

    /**
     * 评价状态
     */
    @ApiModelProperty(value = "评价状态")
    private Integer evaluationStatus;

    /**
     * 支付方式id
     */
    @ApiModelProperty(value = "支付方式id")
    private Long paymentMethodId;

    /**
     * 支付方式名称
     */
    @ApiModelProperty(value = "支付方式名称")
    private String paymentMethodName;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;


//    TODO 订单价格
//    TODO 用户名称
//    TODO 点餐方式


}