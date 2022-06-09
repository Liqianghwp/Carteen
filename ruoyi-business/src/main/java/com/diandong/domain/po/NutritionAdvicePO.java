package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 营养建议PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_nutrition_advice")
@Data
@ApiModel("营养建议PO实体类")
@Accessors(chain = true)
public class NutritionAdvicePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 餐次id
     */
    @TableField(value = "meal_times_id")
    @ApiModelProperty(value = "餐次id")
    private Long mealTimesId;

    /**
     * 餐次名称
     */
    @TableField(value = "meal_times_name")
    @ApiModelProperty(value = "餐次名称")
    private String mealTimesName;

    /**
     * 营养信息id
     */
    @TableField(value = "nutritional_id")
    @ApiModelProperty(value = "营养信息id")
    private Long nutritionalId;

    /**
     * 营养信息名称
     */
    @TableField(value = "nutritional_name")
    @ApiModelProperty(value = "营养信息名称")
    private String nutritionalName;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty(value = "单位")
    private String unit;

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
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
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