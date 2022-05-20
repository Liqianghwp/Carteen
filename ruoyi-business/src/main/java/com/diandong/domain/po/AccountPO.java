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
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-05-20
 */
@TableName("wis_account")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class AccountPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 类别
     */
    @TableField(value = "category")
    @ApiModelProperty(value = "类别")
    private String category;

    /**
     * 原材料名
     */
    @TableField(value = "storage_way")
    @ApiModelProperty(value = "原材料名")
    private String storageWay;

    /**
     * 库存
     */
    @TableField(value = "inventory")
    @ApiModelProperty(value = "库存")
    private Long inventory;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 单价
     */
    @TableField(value = "unit_price")
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    /**
     * 仓库
     */
    @TableField(value = "warehouse")
    @ApiModelProperty(value = "仓库")
    private String warehouse;

    /**
     * 最小库存预警
     */
    @TableField(value = "minimum_inventory_warning")
    @ApiModelProperty(value = "最小库存预警")
    private Long minimumInventoryWarning;

    /**
     * 最大库存预警
     */
    @TableField(value = "minimum_inventory")
    @ApiModelProperty(value = "最大库存预警")
    private Long minimumInventory;

}