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
 * 入库PO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@TableName("wis_inventory_inbound")
@Data
@ApiModel("入库PO实体类")
@Accessors(chain = true)
public class InventoryInboundPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 库存台账id
     */
    @TableField(value = "ledger_id")
    @ApiModelProperty(value = "库存台账id")
    private Long ledgerId;

    /**
     * 入库人
     */
    @TableField(value = "inbounder")
    @ApiModelProperty(value = "入库人")
    private String inbounder;

    /**
     * 原材料图片
     */
    @TableField(value = "raw_material_pic")
    @ApiModelProperty(value = "原材料图片")
    private String rawMaterialPic;

    /**
     * 入库数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "入库数量")
    private Integer number;

    /**
     * 单价
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 入库方式id
     */
    @TableField(value = "inbound_type")
    @ApiModelProperty(value = "入库方式id")
    private Long inboundType;

    /**
     * 入库方式名称
     */
    @TableField(value = "inbound_name")
    @ApiModelProperty(value = "入库方式名称")
    private String inboundName;

    /**
     * 关联单号 这个跟入库方式相关
     */
    @TableField(value = "association")
    @ApiModelProperty(value = "关联单号 这个跟入库方式相关")
    private String association;

    /**
     * 仓库id
     */
    @TableField(value = "warehouse_id")
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    @TableField(value = "warehouse_name")
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    /**
     * 是否记账 0：不记账；1：记账
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "是否记账 0：不记账；1：记账")
    private String state;

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


    @TableField(exist = false)
    @ApiModelProperty(value = "原材料名称")
    private String rawName;

    @ApiModelProperty(value = "总金额")
    @TableField(exist = false)
    private BigDecimal amount;



}