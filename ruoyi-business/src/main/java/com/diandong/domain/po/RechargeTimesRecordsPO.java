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
 * 充次记录PO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@TableName("wis_recharge_times_records")
@Data
@ApiModel("充次记录PO实体类")
@Accessors(chain = true)
public class RechargeTimesRecordsPO implements Serializable {
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
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 充值方式(0:;1:;2:;3:;)
     */
    @TableField(value = "recharge_method")
    @ApiModelProperty(value = "充值方式(0:;1:;2:;3:;)")
    private String rechargeMethod;

    /**
     * 时间
     */
    @TableField(value = "time")
    @ApiModelProperty(value = "时间")
    private LocalDateTime time;

    /**
     * 充值金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;

    /**
     * 次数
     */
    @TableField(value = "count")
    @ApiModelProperty(value = "次数")
    private Integer count;

    /**
     * 剩余次数
     */
    @TableField(value = "remain_times")
    @ApiModelProperty(value = "剩余次数")
    private Integer remainTimes;

    /**
     * 使用开始时间
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value = "使用开始时间")
    private LocalDateTime startTime;

    /**
     * 使用结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value = "使用结束时间")
    private LocalDateTime endTime;

    /**
     * 状态 1：已取消；0：未取消
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态 1：已取消；0：未取消")
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