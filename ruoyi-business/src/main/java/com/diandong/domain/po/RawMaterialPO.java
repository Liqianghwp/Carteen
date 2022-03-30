package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@TableName("wis_raw_material")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class RawMaterialPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂姓名
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂姓名")
    private String canteenName;

    /**
     * 原材料名称
     */
    @TableField(value = "raw_material_name")
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 原材料类别id
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料类别名称
     */
    @TableField(value = "category_name")
    @ApiModelProperty(value = "原材料类别名称")
    private String categoryName;

    /**
     * 单位id
     */
    @TableField(value = "unit_id")
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 单位名称
     */
    @TableField(value = "unit_name")
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 采购类型id
     */
    @TableField(value = "purchase_type_id")
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 采购类型名称
     */
    @TableField(value = "purchase_type_name")
    @ApiModelProperty(value = "采购类型名称")
    private String purchaseTypeName;

    /**
     * 预估进价
     */
    @TableField(value = "pre_price")
    @ApiModelProperty(value = "预估进价")
    private Double prePrice;

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
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 （0:停用；1:启用）
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 （0:停用；1:启用）")
    private Integer status;

    /**
     * 数据状态（0:未删除；1:已删除）
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态（0:未删除；1:已删除）")
    private Integer dataState;

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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新者姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新者姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}