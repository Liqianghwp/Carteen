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
 * 原材料黑名单PO实体类
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@TableName("wis_raw_material_blacklist")
@Data
@ApiModel("原材料黑名单PO实体类")
@Accessors(chain = true)
public class RawMaterialBlacklistPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

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
     * 其他描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "其他描述")
    private String remark;

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