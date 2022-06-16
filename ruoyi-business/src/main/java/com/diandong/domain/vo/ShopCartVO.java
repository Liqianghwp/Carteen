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
 * 购物车VO实体类
 *
 * @author YuLiu
 * @date 2022-06-14
 */
@Data
@ApiModel("购物车VO实体类")
public class ShopCartVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
    @NotNull(groups = {Insert.class}, message = "餐次id不能为空")
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
    @NotNull(groups = {Insert.class}, message = "菜品id不能为空")
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
    @NotNull(groups = {Insert.class}, message = "数量不能为空")
    @ApiModelProperty(value = "数量")
    private Integer number;


}