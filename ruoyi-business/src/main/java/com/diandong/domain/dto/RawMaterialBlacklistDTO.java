package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 原材料黑名单DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-13
 */
@Data
@ApiModel("原材料黑名单DTO实体类")
public class RawMaterialBlacklistDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

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
     * 其他描述
     */
    @ApiModelProperty(value = "其他描述")
    private String remark;

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