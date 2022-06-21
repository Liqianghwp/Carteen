package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 食谱DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("食谱DTO实体类")
public class RecipeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
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
     * 食谱名称
     */
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 食谱日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "食谱日期")
    private LocalDate recipeDate;

    /**
     * 添加方式id
     */
    @ApiModelProperty(value = "添加方式id")
    private Long addWayId;

    /**
     * 添加方式名称
     */
    @ApiModelProperty(value = "添加方式名称")
    private String addWayName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

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

    @ApiModelProperty(value = "食谱详情信息")
    List<RecipeDetailDTO> recipeDetailList;

    /**
     * 早点列表
     */
    @ApiModelProperty(value = "APP：食谱早餐列表")
    List<RecipeDetailDTO> breakfastList;
    /**
     * 午餐列表
     */
    @ApiModelProperty(value = "APP：食谱午餐列表")
    List<RecipeDetailDTO> lunchList;
    /**
     * 晚餐列表
     */
    @ApiModelProperty(value = "APP：食谱晚餐列表")
    List<RecipeDetailDTO> dinnerList;


}