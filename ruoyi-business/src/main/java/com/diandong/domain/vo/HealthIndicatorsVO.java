package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-03-31
 */
@Data
@ApiModel("健康指标VO实体类")
public class HealthIndicatorsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;


}