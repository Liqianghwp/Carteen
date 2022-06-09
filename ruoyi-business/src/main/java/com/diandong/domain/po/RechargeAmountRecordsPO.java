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
 * 充值记录PO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@TableName("wis_recharge_amount_records")
@Data
@ApiModel("充值记录PO实体类")
@Accessors(chain = true)
public class RechargeAmountRecordsPO implements Serializable {
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
    @TableField(value = "serial_number")
    @ApiModelProperty(value = "流水号")
    private String serialNumber;

    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户编号")
    private Long userId;

    /**
     * 充值类型(0:;1:;2:;3:;)
     */
    @TableField(value = "recharge_type")
    @ApiModelProperty(value = "充值类型(0:;1:;2:;3:;)")
    private String rechargeType;

    /**
     * 卡号
     */
    @TableField(value = "card_no")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 充值金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    /**
     * 充值方式(0:;1:;2:;3:;)
     */
    @TableField(value = "recharge_method")
    @ApiModelProperty(value = "充值方式(0:;1:;2:;3:;)")
    private String rechargeMethod;

    /**
     * 状态 0：未取消；1：已取消
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态 0：未取消；1：已取消")
    private Integer state;

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