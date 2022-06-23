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
 * 采购清单管理PO实体类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@TableName("wis_purchasing")
@Data
@ApiModel("采购清单管理PO实体类")
@Accessors(chain = true)
public class PurchasingPO implements Serializable {
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
     * 数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "数量")
    private Double number;

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
     * 单价
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 是否采购 0：未采购；1：已采购
     */
    @TableField(value = "is_purchase")
    @ApiModelProperty(value = "是否采购 0：未采购；1：已采购")
    private Integer isPurchase;

    /**
     * 小计
     */
    @TableField(value = "subtotal")
    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

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