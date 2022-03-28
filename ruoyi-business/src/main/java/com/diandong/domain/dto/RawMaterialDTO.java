package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-03-25
 */
@Data
@ApiModel("DTO实体类")
public class RawMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 原材料类别id
     */
    @ApiModelProperty(value = "原材料类别id")
    private Long categoryId;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id")
    private Long unitId;

    /**
     * 采购类型id
     */
    @ApiModelProperty(value = "采购类型id")
    private Long purchaseTypeId;

    /**
     * 预估进价
     */
    @ApiModelProperty(value = "预估进价")
    private BigDecimal prePrice;

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
     * 其他描述
     */
    @ApiModelProperty(value = "其他描述")
    private String remark;

    /**
     * 状态（0:停用，1：启用）
     */
    @ApiModelProperty(value = "状态（0:停用，1：启用）")
    private String status;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}