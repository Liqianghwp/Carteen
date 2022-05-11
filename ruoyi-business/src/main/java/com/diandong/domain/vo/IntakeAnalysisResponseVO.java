package com.diandong.domain.vo;

import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@ApiModel(value = "营养摄入分析返回实体类")
@Data
public class IntakeAnalysisResponseVO extends BaseEntity implements Serializable {

    /**
     * 录入营养摄入信息
     */
    @ApiModelProperty(value = "录入营养摄入信息")
    private Map<String,Double> inputMap;

    /**
     * 建议营养摄入信息
     */
    @ApiModelProperty(value = "建议营养摄入信息")
    private Map<String,Double> suggestionMap;

}
