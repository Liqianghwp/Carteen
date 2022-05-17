package com.diandong.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Classname RecipePieSituation
 * @Description 派菜情况实体
 * @Date 2022/5/16 14:15
 * @Created by YuLiu
 */
@ApiModel(value = "派菜情况实体入参类")
@Data
public class RecipePieSituationVO implements Serializable {
    private static final long serialVersionUID = 434597758375013501L;

    @NotNull(message = "菜品id不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    @NotNull(message = "月份不能为空")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "表示月份的日期", example = "如果是2022年5月份,入参：2022-05-01")
    private LocalDate date;

}
