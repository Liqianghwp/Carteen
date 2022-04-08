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
 * @date 2022-04-08
 */
@Data
@ApiModel("DTO实体类")
public class HealthIndicatorsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 指标ID
     */
    @ApiModelProperty(value = "指标ID")
    private Long indicatorsId;

    /**
     * 指标名称
     */
    @ApiModelProperty(value = "指标名称")
    private String indicatorsName;

    /**
     * 指标数值
     */
    @ApiModelProperty(value = "指标数值")
    private Double indicatorValue;

    /**
     * 指标单位
     */
    @ApiModelProperty(value = "指标单位")
    private String indicatorUnit;

    /**
     * 指标所属人id
     */
    @ApiModelProperty(value = "指标所属人id")
    private Long userId;

    /**
     * 指标所属人名称
     */
    @ApiModelProperty(value = "指标所属人名称")
    private String userName;

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
     * 创建时间 当前时间
     */
    @ApiModelProperty(value = "创建时间 当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
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