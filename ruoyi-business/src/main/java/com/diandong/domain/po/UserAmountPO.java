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
 * 用户金额PO实体类
 *
 * @author YuLiu
 * @date 2022-06-07
 */
@TableName("wis_user_amount")
@Data
@ApiModel("用户金额PO实体类")
@Accessors(chain = true)
public class UserAmountPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 补贴
     */
    @TableField(value = "subsidy")
    @ApiModelProperty(value = "补贴")
    private BigDecimal subsidy;

    /**
     * 次数
     */
    @TableField(value = "times")
    @ApiModelProperty(value = "次数")
    private Integer times;

    /**
     * 类型 0：使用金钱；1：使用次数
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "类型 0：使用金钱；1：使用次数")
    private Integer type;

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