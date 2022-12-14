package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 食谱详情PO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@TableName("wis_recipe_detail")
@Data
@ApiModel("食谱详情PO实体类")
@Accessors(chain = true)
public class RecipeDetailPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食谱id
     */
    @TableField(value = "recipe_id")
    @ApiModelProperty(value = "食谱id")
    private Long recipeId;

    /**
     * 食谱名称
     */
    @TableField(value = "recipe_name")
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 餐次编号
     */
    @TableField(value = "meal_times_id")
    @ApiModelProperty(value = "餐次编号")
    private Long mealTimesId;

    /**
     * 餐次名称
     */
    @TableField(value = "meal_times_name")
    @ApiModelProperty(value = "餐次名称")
    private String mealTimesName;

    /**
     * 菜品id
     */
    @TableField(value = "dishes_id")
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @TableField(value = "dishes_name")
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 菜品类别id
     */
    @TableField(value = "dishes_type_id")
    @ApiModelProperty(value = "菜品类别id")
    private Long dishesTypeId;

    /**
     * 菜品类别名称
     */
    @TableField(value = "dishes_type_name")
    @ApiModelProperty(value = "菜品类别名称")
    private String dishesTypeName;

    /**
     * 厨师id
     */
    @TableField(value = "chef_id")
    @ApiModelProperty(value = "厨师id")
    private Long chefId;

    /**
     * 厨师名称
     */
    @TableField(value = "chef_name")
    @ApiModelProperty(value = "厨师名称")
    private String chefName;

    /**
     * 数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "数量")
    private Integer number;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}