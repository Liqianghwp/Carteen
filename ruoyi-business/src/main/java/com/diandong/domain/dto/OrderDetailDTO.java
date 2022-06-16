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

/**
 * 订单详情DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("订单详情DTO实体类")
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 餐次id
     */
    @ApiModelProperty(value = "订单id")
    private Long mealTimesId;

    /**
     * 餐次名称
     */
    @ApiModelProperty(value = "订单id")
    private String mealTimesName;
    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 菜品价格
     */
    @ApiModelProperty(value = "菜品价格")
    private BigDecimal dishesPrice;

    /**
     * 菜品数量
     */
    @ApiModelProperty(value = "菜品数量")
    private Integer dishesCount;

    /**
     * 菜品总价
     */
    @ApiModelProperty(value = "菜品总价")
    private BigDecimal dishesTotalPrice;

    /**
     * 菜品图片 图片地址（后期如果没有了怎么办）
     */
    @ApiModelProperty(value = "菜品图片 图片地址（后期如果没有了怎么办）")
    private String dishesPicture;

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

}