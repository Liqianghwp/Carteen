package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品评价记录DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Data
@ApiModel("菜品评价记录DTO实体类")
public class DishesEvaluationsRecordsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 好评数量
     */
    @ApiModelProperty(value = "好评数量")
    private Integer goodNum;

    /**
     * 差评数量
     */
    @ApiModelProperty(value = "差评数量")
    private Integer badNum;

    /**
     * 好评度
     */
    @ApiModelProperty(value = "好评度")
    private Double praise;

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