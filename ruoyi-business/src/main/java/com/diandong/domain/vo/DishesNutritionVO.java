package com.diandong.domain.vo;

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
 * 菜品营养信息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("菜品营养信息VO实体类")
public class DishesNutritionVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @NotNull(groups = {Insert.class},message = "菜品ID不能为空")
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
    @NotNull(groups = {Insert.class},message = "营养信息不能为空")
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


}