package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("订单DTO实体类")
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 订单详情
     */
    @ApiModelProperty(value = "订单详情")
    private List<OrderDetailDTO> orderDetailList;
}