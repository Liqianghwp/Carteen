package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配料管理详情PO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@TableName("wis_ingredients_detail")
@Data
@ApiModel("配料管理详情PO实体类")
@Accessors(chain = true)
public class IngredientsDetailPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 父级id
     */
    @TableField(value = "ingredients_id")
    @ApiModelProperty(value = "父级id")
    private Long ingredientsId;

    /**
     * 配料类型 0：主料；1：辅料
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "配料类型 0：主料；1：辅料")
    private String type;

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
     * 重量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "重量")
    private Double number;

    /**
     * 是否删除 0:否;1:是
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "是否删除 0:否;1:是")
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