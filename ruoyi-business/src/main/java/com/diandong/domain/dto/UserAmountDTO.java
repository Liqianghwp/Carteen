package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户金额DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Data
@ApiModel("用户金额DTO实体类")
public class UserAmountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 补贴
     */
    @ApiModelProperty(value = "补贴")
    private BigDecimal subsidy;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    private Integer times;

    /**
     * 类型 0：使用金钱；1：使用次数
     */
    @ApiModelProperty(value = "类型 0：使用金钱；1：使用次数")
    private Integer type;

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