package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@TableName("wis_health_indicators")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class HealthIndicatorsPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 腰围
     */
    @TableField(value = "waistline")
    @ApiModelProperty(value = "腰围")
    private Long waistline;

    /**
     * 体重
     */
    @TableField(value = "body_weight")
    @ApiModelProperty(value = "体重")
    private Long bodyWeight;

    /**
     * 体脂
     */
    @TableField(value = "body_fat")
    @ApiModelProperty(value = "体脂")
    private Long bodyFat;

    /**
     * 心率
     */
    @TableField(value = "heart_rate")
    @ApiModelProperty(value = "心率")
    private Long heartRate;

    /**
     * 血压
     */
    @TableField(value = "blood_pressure")
    @ApiModelProperty(value = "血压")
    private Long bloodPressure;

    /**
     * 血糖
     */
    @TableField(value = "blood_sugar")
    @ApiModelProperty(value = "血糖")
    private Long bloodSugar;

    /**
     * 甘油三酯
     */
    @TableField(value = "triglycerides")
    @ApiModelProperty(value = "甘油三酯")
    private Long triglycerides;

    /**
     * 总胆固醇
     */
    @TableField(value = "total_cholesterol")
    @ApiModelProperty(value = "总胆固醇")
    private Long totalCholesterol;

    /**
     * 坏胆固醇
     */
    @TableField(value = "bad_cholesterol")
    @ApiModelProperty(value = "坏胆固醇")
    private Long badCholesterol;

    /**
     * 血氧
     */
    @TableField(value = "blood_oxygen")
    @ApiModelProperty(value = "血氧")
    private Long bloodOxygen;

    /**
     * 血粘稠度
     */
    @TableField(value = "blood_viscosity")
    @ApiModelProperty(value = "血粘稠度")
    private Long bloodViscosity;

    /**
     * 数据状态
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}