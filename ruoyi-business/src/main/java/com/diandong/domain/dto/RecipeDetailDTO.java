package com.diandong.domain.dto;

import com.diandong.service.DishesMpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 食谱详情DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("食谱详情DTO实体类")
public class RecipeDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食谱id
     */
    @ApiModelProperty(value = "食谱id")
    private Long recipeId;

    /**
     * 食谱名称
     */
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 餐次编号
     */
    @ApiModelProperty(value = "餐次编号")
    private Long mealTimesId;

    /**
     * 餐次名称
     */
    @ApiModelProperty(value = "餐次名称")
    private String mealTimesName;

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
     * 菜品类别id
     */
    @ApiModelProperty(value = "菜品类别id")
    private Long dishesTypeId;

    /**
     * 菜品类别名称
     */
    @ApiModelProperty(value = "菜品类别名称")
    private String dishesTypeName;
    /**
     * 厨师id
     */
    @ApiModelProperty(value = "厨师id")
    private Long chefId;

    /**
     * 厨师名称
     */
    @ApiModelProperty(value = "厨师名称")
    private String chefName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer number;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 当前时间
     */
    @ApiModelProperty(value = "创建时间 当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 菜品图片
     */
    @ApiModelProperty(value = "菜品图片")
    private String dishesPicture;

    /**
     * 菜品信息
     */
    @ApiModelProperty(value = "菜品信息")
    private DishesDTO dishes;
}