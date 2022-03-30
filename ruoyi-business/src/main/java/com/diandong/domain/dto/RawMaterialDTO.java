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
 * @date 2022-03-29
 */
@Data
@ApiModel("DTO实体类")
public class RawMaterialDTO implements Serializable {
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
     * 食堂姓名
     */
    @ApiModelProperty(value = "食堂姓名")
    private String canteenName;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 原材料类别id
     */
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料类别名称
     */
    @ApiModelProperty(value = "原材料类别名称")
    private String categoryName;

    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 采购类型id
     */
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 采购类型名称
     */
    @ApiModelProperty(value = "采购类型名称")
    private String purchaseTypeName;

    /**
     * 预估进价
     */
    @ApiModelProperty(value = "预估进价")
    private Double prePrice;

    /**
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Long storehouseId;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String storehouseName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 （0:停用；1:启用）
     */
    @ApiModelProperty(value = "状态 （0:停用；1:启用）")
    private Integer status;

    /**
     * 数据状态（0:未删除；1:已删除）
     */
    @ApiModelProperty(value = "数据状态（0:未删除；1:已删除）")
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
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新者姓名
     */
    @ApiModelProperty(value = "更新者姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}