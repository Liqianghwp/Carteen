package com.diandong.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname UserNutritionAdviceVO
 * @Description 用户营养建议
 * @Date 2022/5/31 18:37
 * @Created by YuLiu
 */
@Data
public class UserNutritionAdviceVO implements Serializable {

    private static final long serialVersionUID = 2178881848838695635L;

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
    private List<NutritionAdviceVO> nutritionAdviceList;

}
