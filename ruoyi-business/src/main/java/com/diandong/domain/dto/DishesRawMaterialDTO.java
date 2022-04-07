package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-03-31
 */
@Data
@ApiModel("菜品原料DTO实体类")
public class DishesRawMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

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
     * 原材料id
     */
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Double number;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
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

}