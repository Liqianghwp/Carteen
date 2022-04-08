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
 * @date 2022-04-08
 */
@Data
@ApiModel("VO实体类")
public class HealthIndicatorsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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