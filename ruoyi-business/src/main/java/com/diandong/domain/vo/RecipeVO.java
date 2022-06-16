package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 食谱VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("食谱VO实体类")
public class RecipeVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食谱名称
     */
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 食谱日期
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "食谱日期")
    private LocalDate recipeDate;

    /**
     * 添加方式id
     */
    @ApiModelProperty(value = "添加方式id")
    private Long addWayId;

    /**
     * 添加方式名称
     */
    @ApiModelProperty(value = "添加方式名称")
    private String addWayName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 食谱详情信息
     */
    @Valid
    @ApiModelProperty(value = "食谱详情信息")
    List<RecipeDetailVO> recipeDetailList;
}