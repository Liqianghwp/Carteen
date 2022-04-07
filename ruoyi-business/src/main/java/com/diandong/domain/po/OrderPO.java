package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-30
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
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(value = "data_status")
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 订单菜品集合
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "订单菜品集合")
    private List<OrderDetailPO> orderDetailList;

}