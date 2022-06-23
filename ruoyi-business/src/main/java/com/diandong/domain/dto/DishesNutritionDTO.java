package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品营养信息DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("菜品营养信息DTO实体类")
public class DishesNutritionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 营养信息id
     */
    @ApiModelProperty(value = "营养信息id")
    private Long nutritionId;

    /**
     * 营养信息名称
     */
    @ApiModelProperty(value = "营养信息名称")
    private String nutritionName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Double number;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建日期 默认为当前时间
     */
    @ApiModelProperty(value = "创建日期 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新人时间
     */
    @ApiModelProperty(value = "更新人时间")
    private LocalDateTime updateTime;

}