package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配料管理PO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@TableName("wis_ingredients")
@Data
@ApiModel("配料管理PO实体类")
@Accessors(chain = true)
public class IngredientsPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 菜品类别id
     */
    @TableField(value = "dish_type_id")
    @ApiModelProperty(value = "菜品类别id")
    private Long dishTypeId;

    /**
     * 菜品类别名称
     */
    @TableField(value = "dish_type_name")
    @ApiModelProperty(value = "菜品类别名称")
    private String dishTypeName;

    /**
     * 菜品id
     */
    @TableField(value = "dish_id")
    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    /**
     * 菜品名称
     */
    @TableField(value = "dish_name")
    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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