package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 购物车DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-14
 */
@Data
@ApiModel("购物车DTO实体类")
public class ShopCartDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 就餐食堂id
     */
    @ApiModelProperty(value = "就餐食堂id")
    private Long canteenId;

    /**
     * 就餐食堂名称
     */
    @ApiModelProperty(value = "就餐食堂名称")
    private String canteenName;

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
     * 菜品规格
     */
    @ApiModelProperty(value = "菜品规格")
    private String dishesSpecification;

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
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
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

}