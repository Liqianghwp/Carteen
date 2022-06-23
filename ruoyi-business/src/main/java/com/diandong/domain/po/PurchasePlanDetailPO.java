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
 * 采购计划单详情PO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@TableName("wis_purchase_plan_detail")
@Data
@ApiModel("采购计划单详情PO实体类")
@Accessors(chain = true)
public class PurchasePlanDetailPO implements Serializable {
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
     * 采购计划单id
     */
    @TableField(value = "plan_id")
    @ApiModelProperty(value = "采购计划单id")
    private String planId;

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
     * 原材料id
     */
    @TableField(value = "raw_material_id")
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

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
     * 单位名称
     */
    @TableField(value = "unit_name")
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 上次进价
     */
    @TableField(value = "last_purchase")
    @ApiModelProperty(value = "上次进价")
    private BigDecimal lastPurchase;

    /**
     * 采购数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "采购数量")
    private Double number;

    /**
     * 询问单价
     */
    @TableField(value = "current_price")
    @ApiModelProperty(value = "询问单价")
    private BigDecimal currentPrice;

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