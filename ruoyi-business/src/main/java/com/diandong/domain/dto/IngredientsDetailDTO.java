package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配料管理详情DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Data
@ApiModel("配料管理详情DTO实体类")
public class IngredientsDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long ingredientsId;

    /**
     * 配料类型 0：主料；1：辅料
     */
    @ApiModelProperty(value = "配料类型 0：主料；1：辅料")
    private String type;

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
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double number;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}