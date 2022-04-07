package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Data
@ApiModel("食谱信息DTO实体类")
public class RecipeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食谱名称
     */
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 食谱日期
     */
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
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 菜谱菜品信息
     */
    @ApiModelProperty(value = "菜谱菜品信息")
    private List<RecipeDetailDTO> recipeDetailDTOList;
}