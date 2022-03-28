package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-03-28
 */
@Data
@ApiModel("VO实体类")
public class DishesNutritionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long disherId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private Long disherName;

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
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;


}