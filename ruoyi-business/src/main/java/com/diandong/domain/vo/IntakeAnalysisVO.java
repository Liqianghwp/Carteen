package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "营养摄入分析VO类")
@Data
public class IntakeAnalysisVO implements Serializable {
    private static final long serialVersionUID = 1625779383779911464L;


    /**
     * 餐次id
     */
    @ApiModelProperty(value = "餐次id")
    private Long mealTimesId;

    /**
     * 营养id
     */
    @NotNull(message = "营养id不能为空")
    @ApiModelProperty(value = "营养id")
    private Long nutritionId;
    /**
     * 查询开始时间
     */
    @NotNull(message = "查询开始时间不能为空")
    @ApiModelProperty(value = "查询开始时间")
    private LocalDateTime startTime;

    /**
     * 查询结束时间
     */
    @NotNull(message = "查询结束时间不能为空")
    @ApiModelProperty(value = "查询结束时间")
    private LocalDateTime endTime;

}
