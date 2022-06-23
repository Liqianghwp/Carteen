package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品营养信息PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_dishes_nutrition")
@Data
@ApiModel("菜品营养信息PO实体类")
@Accessors(chain = true)
public class DishesNutritionPO implements Serializable {
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
     * 营养信息id
     */
    @TableField(value = "nutrition_id")
    @ApiModelProperty(value = "营养信息id")
    private Long nutritionId;

    /**
     * 营养信息名称
     */
    @TableField(value = "nutrition_name")
    @ApiModelProperty(value = "营养信息名称")
    private String nutritionName;

    /**
     * 数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "数量")
    private Double number;

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
     * 创建日期 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新人时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人时间")
    private LocalDateTime updateTime;

}