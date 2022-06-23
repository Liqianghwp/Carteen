package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购订单管理PO实体类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@TableName("wis_purchase_order")
@Data
@ApiModel("采购订单管理PO实体类")
@Accessors(chain = true)
public class PurchaseOrderPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 集团id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "集团id")
    private Long groupId;

    /**
     * 采购订单号
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "采购订单号")
    private String orderId;

    /**
     * 供应商id
     */
    @TableField(value = "supplier_id")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @TableField(value = "supplier_name")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 订单总金额
     */
    @TableField(value = "order_price")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderPrice;

    /**
     * 状态 0：采购中；1：部分入库；2：入库成功
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态 0：采购中；1：部分入库；2：入库成功")
    private Integer state;

    /**
     * 采购清单id集合
     */
    @TableField(value = "details")
    @ApiModelProperty(value = "采购清单id集合")
    private String details;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}