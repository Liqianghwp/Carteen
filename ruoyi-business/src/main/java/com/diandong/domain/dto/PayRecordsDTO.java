package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-17
 */
@Data
@ApiModel("支付记录DTO实体类")
public class PayRecordsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String serialId;

    /**
     * 支付类型（0:订单；1：充次；2：充数）
     */
    @ApiModelProperty(value = "支付类型（0:订单；1：充次；2：充数）")
    private Integer type;

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private Integer payFlag;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    /**
     * 支付人
     */
    @ApiModelProperty(value = "支付人")
    private Long userId;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    private String payNo;

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