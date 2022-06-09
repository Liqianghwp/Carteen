package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值记录DTO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@Data
@ApiModel("充值记录DTO实体类")
public class RechargeAmountRecordsDTO implements Serializable {
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
    private String serialNumber;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private Long userId;

    /**
     * 充值类型(0:;1:;2:;3:;)
     */
    @ApiModelProperty(value = "充值类型(0:;1:;2:;3:;)")
    private String rechargeType;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    /**
     * 充值方式(0:;1:;2:;3:;)
     */
    @ApiModelProperty(value = "充值方式(0:;1:;2:;3:;)")
    private String rechargeMethod;

    /**
     * 状态 0：未取消；1：已取消
     */
    @ApiModelProperty(value = "状态 0：未取消；1：已取消")
    private Integer state;

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