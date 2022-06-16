package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 食谱详情VO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("食谱详情VO实体类")
public class RecipeDetailVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @NotNull(message = "餐次编号不能为空")
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
    @NotNull(message = "菜品id不能为空")
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
    @NotNull(message = "厨师id不能为空")
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
    @NotNull(message = "数量不能为空")
    @ApiModelProperty(value = "数量")
    private Integer number;


}