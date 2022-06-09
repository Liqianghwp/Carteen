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
 * 补贴记录PO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@TableName("wis_subsidy_records")
@Data
@ApiModel("补贴记录PO实体类")
@Accessors(chain = true)
public class SubsidyRecordsPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    @ApiModelProperty(value = "用户类型")
    private Long userType;

    /**
     * 用户集合
     */
    @TableField(value = "user_ids")
    @ApiModelProperty(value = "用户集合")
    private String userIds;

    /**
     * 补贴金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "补贴金额")
    private BigDecimal amount;

    /**
     * 类型 0：新增；1：清零
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "类型 0：新增；1：清零")
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