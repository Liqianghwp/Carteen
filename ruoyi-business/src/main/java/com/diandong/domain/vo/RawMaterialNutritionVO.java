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
 * @date 2022-03-25
 */
@Data
@ApiModel("VO实体类")
public class RawMaterialNutritionVO implements Serializable {
//    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 原材料id
     */
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

    /**
     * 营养参数id
     */
    @ApiModelProperty(value = "营养参数id")
    private Long nutritionParamsId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer number;


}