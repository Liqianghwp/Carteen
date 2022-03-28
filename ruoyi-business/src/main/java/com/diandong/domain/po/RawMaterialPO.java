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
 * @date 2022-03-25
 */
@TableName("raw_material")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class RawMaterialPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 原材料类别id
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料名称
     */
    @TableField(value = "raw_material_name")
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 单位id
     */
    @TableField(value = "unit_id")
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 采购类型id
     */
    @TableField(value = "purchase_type_id")
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 预估进价
     */
    @TableField(value = "pre_price")
    @ApiModelProperty(value = "预估进价")
    private BigDecimal prePrice;

    /**
     * 仓库id
     */
    @TableField(value = "storehouse_id")
    @ApiModelProperty(value = "仓库id")
    private Long storehouseId;

    /**
     * 仓库名称
     */
    @TableField(value = "storehouse_name")
    @ApiModelProperty(value = "仓库名称")
    private String storehouseName;

    /**
     * 其他描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "其他描述")
    private String remark;

    /**
     * 状态（0:停用，1：启用）
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态（0:停用，1：启用）")
    private String status;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}