package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录PO实体类
 *
 * @author YuLiu
 * @date 2022-06-17
 */
@TableName("wis_pay_records")
@Data
@ApiModel("支付记录PO实体类")
@Accessors(chain = true)
public class PayRecordsPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 流水号
     */
    @TableField(value = "serial_id")
    @ApiModelProperty(value = "流水号")
    private String serialId;

    /**
     * 支付类型（0:订单；1：充次；2：充数）
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "支付类型（0:订单；1：充次；2：充数）")
    private Integer type;

    /**
     * 支付状态
     */
    @TableField(value = "pay_flag")
    @ApiModelProperty(value = "支付状态")
    private Integer payFlag;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    /**
     * 支付人
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "支付人")
    private Long userId;

    /**
     * 支付方式
     */
    @TableField(value = "pay_way")
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    /**
     * 支付流水号
     */
    @TableField(value = "pay_no")
    @ApiModelProperty(value = "支付流水号")
    private String payNo;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}