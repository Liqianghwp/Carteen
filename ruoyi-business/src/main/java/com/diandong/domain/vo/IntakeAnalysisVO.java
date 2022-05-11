package com.diandong.domain.vo;

import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "营养摄入分析VO类")
@Data
public class IntakeAnalysisVO extends BaseEntity implements Serializable {
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

}
