package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 健康指标VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("健康指标VO实体类")
public class HealthIndicatorsVO extends BaseEntity implements Serializable {
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


}