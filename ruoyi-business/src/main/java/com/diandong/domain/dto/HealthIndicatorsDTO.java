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
@ApiModel("DTO实体类")
public class HealthIndicatorsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 腰围
     */
    @ApiModelProperty(value = "腰围")
    private Double waistline;

    /**
     * 体重
     */
    @ApiModelProperty(value = "体重")
    private Double bodyWeight;

    /**
     * 体脂
     */
    @ApiModelProperty(value = "体脂")
    private Double bodyFat;

    /**
     * 心率
     */
    @ApiModelProperty(value = "心率")
    private Double heartRate;

    /**
     * 血压
     */
    @ApiModelProperty(value = "血压")
    private Double bloodPressure;

    /**
     * 血糖
     */
    @ApiModelProperty(value = "血糖")
    private Double bloodSugar;

    /**
     * 甘油三酯
     */
    @ApiModelProperty(value = "甘油三酯")
    private Double triglycerides;

    /**
     * 总胆固醇
     */
    @ApiModelProperty(value = "总胆固醇")
    private Double totalCholesterol;

    /**
     * 坏胆固醇
     */
    @ApiModelProperty(value = "坏胆固醇")
    private Double badCholesterol;

    /**
     * 血氧
     */
    @ApiModelProperty(value = "血氧")
    private Double bloodOxygen;

    /**
     * 血粘稠度
     */
    @ApiModelProperty(value = "血粘稠度")
    private Double bloodViscosity;

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