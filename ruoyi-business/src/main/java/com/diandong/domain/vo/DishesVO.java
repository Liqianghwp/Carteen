package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-03-25
 */
@Data
@ApiModel("VO实体类")
public class DishesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 菜品分类id
     */
    @ApiModelProperty(value = "菜品分类id")
    private Long dishesTypeId;

    /**
     * 菜品分类名称
     */
    @ApiModelProperty(value = "菜品分类名称")
    private String dishesTypeName;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 菜品价格
     */
    @ApiModelProperty(value = "菜品价格")
    private BigDecimal dishesPrice;

    /**
     * 菜品单位
     */
    @ApiModelProperty(value = "菜品单位")
    private String dishesUnit;

    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    private String specification;

    /**
     * 预估价
     */
    @ApiModelProperty(value = "预估价")
    private BigDecimal prePrice;

    /**
     * 产地
     */
    @ApiModelProperty(value = "产地")
    private String origin;

    /**
     * 菜品属性id(早中晚餐的那个id）
     */
    @ApiModelProperty(value = "菜品属性id(早中晚餐的那个id）")
    private Long dishesAttrId;

    /**
     * 菜品属性名称
     */
    @ApiModelProperty(value = "菜品属性名称")
    private String dishesAttrName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 菜品图片
     */
    @ApiModelProperty(value = "菜品图片")
    private String dishesPicture;

    /**
     * 菜品介绍
     */
    @ApiModelProperty(value = "菜品介绍")
    private String dishesIntroduction;

    /**
     * 状态（上下架）
     */
    @ApiModelProperty(value = "状态（上下架）")
    private Integer state;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private String version;

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