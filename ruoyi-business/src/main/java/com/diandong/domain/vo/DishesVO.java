package com.diandong.domain.vo;

import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 菜品信息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("菜品信息VO实体类")
public class DishesVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 菜品代码
     */
    @ApiModelProperty(value = "菜品代码")
    private String code;

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
     * 内部价格
     */
    @ApiModelProperty(value = "内部价格")
    private BigDecimal prePrice;

    /**
     * 产地
     */
    @ApiModelProperty(value = "产地")
    private String origin;

    /**
     * 菜品属性id
     */
    @ApiModelProperty(value = "菜品属性id")
    private String dishesAttrId;

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
     * 状态（0：下架；1：上架）
     */
    @ApiModelProperty(value = "状态（0：下架；1：上架）")
    private Integer state;

    /**
     * 检测报告
     */
    @ApiModelProperty(value = "检测报告")
    private String testReport;


    @ApiModelProperty(value = "原材料信息")
    private List<DishesRawMaterialVO> dishesRawMaterialList;

    @ApiModelProperty(value = "营养信息")
    private List<DishesNutritionVO> dishesNutritionList;

    @ApiModelProperty(value = "原材料供应商信息")
    private List<DishesSupplierVO> dishesSupplierList;

    @ApiModelProperty(value = "添加剂信息")
    private List<DishesAdditiveVO> dishesAdditiveList;

    /**
     * 餐次id
     */
    @ApiModelProperty(value = "餐次id")
    private Long mealTimesId;


}