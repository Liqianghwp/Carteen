package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 健康证到期消息PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_health_cert_msg")
@Data
@ApiModel("健康证到期消息PO实体类")
@Accessors(chain = true)
public class HealthCertMsgPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 健康证id
     */
    @TableField(value = "health_cert_id")
    @ApiModelProperty(value = "健康证id")
    private Long healthCertId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 预警时间
     */
    @TableField(value = "warning_time")
    @ApiModelProperty(value = "预警时间")
    private LocalDateTime warningTime;

    /**
     * 到期前提醒
     */
    @TableField(value = "remind_day")
    @ApiModelProperty(value = "到期前提醒")
    private String remindDay;

    /**
     * 状态 0：未读；1：已读
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态 0：未读；1：已读")
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
    private String createBy;

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
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}