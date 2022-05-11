package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 健康指标PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_health_indicators")
@Data
@ApiModel("健康指标PO实体类")
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
     * 指标ID
     */
    @TableField(value = "indicators_id")
    @ApiModelProperty(value = "指标ID")
    private Long indicatorsId;

    /**
     * 指标名称
     */
    @TableField(value = "indicators_name")
    @ApiModelProperty(value = "指标名称")
    private String indicatorsName;

    /**
     * 指标数值
     */
    @TableField(value = "indicator_value")
    @ApiModelProperty(value = "指标数值")
    private Double indicatorValue;

    /**
     * 指标单位
     */
    @TableField(value = "indicator_unit")
    @ApiModelProperty(value = "指标单位")
    private String indicatorUnit;

    /**
     * 指标所属人id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "指标所属人id")
    private Long userId;

    /**
     * 指标所属人名称
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value = "指标所属人名称")
    private String userName;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

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
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}