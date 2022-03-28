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
 * @date 2022-03-25
 */
@TableName("raw_material_nutrition")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class RawMaterialNutritionPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 原材料id
     */
    @TableField(value = "raw_material_id")
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

    /**
     * 营养参数id
     */
    @TableField(value = "nutrition_params_id")
    @ApiModelProperty(value = "营养参数id")
    private Long nutritionParamsId;

    /**
     * 数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "数量")
    private Integer number;

    /**
     * 是否删除（0:未删除；1:删除）
     */
    @TableField(value = "is_del")
    @ApiModelProperty(value = "是否删除（0:未删除；1:删除）")
    private Integer isDel;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}