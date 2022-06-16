package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_order")
@Data
@ApiModel("订单PO实体类")
@Accessors(chain = true)
public class OrderPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂编号
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂编号")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 订单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 下单时间
     */
    @TableField(value = "order_time")
    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    /**
     * 订单总价格
     */
    @TableField(value = "order_total_price")
    @ApiModelProperty(value = "订单总价格")
    private BigDecimal orderTotalPrice;

    /**
     * 菜品总数量
     */
    @TableField(value = "dishes_total_count")
    @ApiModelProperty(value = "菜品总数量")
    private Integer dishesTotalCount;

    /**
     * 评价状态
     */
    @TableField(value = "evaluation_status")
    @ApiModelProperty(value = "评价状态")
    private Integer evaluationStatus;

    /**
     * 支付方式id
     */
    @TableField(value = "payment_method_id")
    @ApiModelProperty(value = "支付方式id")
    private Long paymentMethodId;

    /**
     * 支付方式名称
     */
    @TableField(value = "payment_method_name")
    @ApiModelProperty(value = "支付方式名称")
    private String paymentMethodName;

    /**
     * 支付时间
     */
    @TableField(value = "payment_time")
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 订单详情
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "订单详情")
    List<OrderDetailPO> orderDetailList;

}