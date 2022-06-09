package com.diandong.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname UserNutritionAdviceDTO
 * @Description 用户营养建议
 * @Date 2022/6/1 10:37
 * @Created by YuLiu
 */
@Data
public class UserNutritionAdviceDTO implements Serializable {
    private static final long serialVersionUID = 6689103629071419162L;

    /**
     * 餐次id
     */
    @ApiModelProperty(value = "餐次id")
    private Long mealTimesId;

    /**
     * 餐次名称
     */
    @ApiModelProperty(value = "餐次名称")
    private String mealTimesName;

    /**
     * 餐次营养建议信息
     */
    @ApiModelProperty(value = "餐次营养建议信息")
    private List<NutritionAdviceDTO> nutritionAdviceList;


}
