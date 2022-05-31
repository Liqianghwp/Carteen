package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 出库PO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@TableName("wis_inventory_outbound")
@Data
@ApiModel("出库PO实体类")
@Accessors(chain = true)
public class InventoryOutboundPO implements Serializable {
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
     * 领取人
     */
    @TableField(value = "recipients")
    @ApiModelProperty(value = "领取人")
    private String recipients;

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
     * 出库数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "出库数量")
    private Long number;

    /**
     * 出库方式id
     */
    @TableField(value = "outbound_type")
    @ApiModelProperty(value = "出库方式id")
    private Long outboundType;

    /**
     * 出库方式名称
     */
    @TableField(value = "outbound_name")
    @ApiModelProperty(value = "出库方式名称")
    private String outboundName;

    /**
     * 关联单号 这个可以根据不同的出库方式显示不同的单号
     */
    @TableField(value = "association")
    @ApiModelProperty(value = "关联单号 这个可以根据不同的出库方式显示不同的单号")
    private String association;

    /**
     * 单价
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "单价")
    private Long price;

    /**
     * 原材料图片
     */
    @TableField(value = "raw_material_pic")
    @ApiModelProperty(value = "原材料图片")
    private String rawMaterialPic;

    /**
     * 是否记账 0：不记账（默认）；1：记账
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "是否记账 0：不记账（默认）；1：记账")
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

}