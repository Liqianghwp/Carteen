package com.diandong.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预留样品DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("预留样品DTO实体类")
public class ReserveSampleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 留样食堂ID
     */
    @ApiModelProperty(value = "留样食堂ID")
    private Long reserveCanteenId;

    /**
     * 留样日期
     */
    @Excel(name = "留样日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "留样日期")
    private LocalDateTime reserveDate;

    /**
     * 餐次
     */
    @ApiModelProperty(value = "餐次")
    private Long mealTimes;

    /**
     * 食品名称
     */
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 留样数量
     */
    @ApiModelProperty(value = "留样数量")
    private String reserveNum;

    /**
     * 储存地点
     */
    @ApiModelProperty(value = "储存地点")
    private Long storageLocation;

    /**
     * 留样人
     */
    @ApiModelProperty(value = "留样人")
    private String reserveName;

    /**
     * 预警周期
     */
    @ApiModelProperty(value = "预警周期")
    private Integer warningDay;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 0：未处理-有效；1：未处理-报警；2：已处理
     */
    @ApiModelProperty(value = "状态 0：未处理-有效；1：未处理-报警；2：已处理")
    private Integer state;

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