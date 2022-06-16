package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值次数设置PO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@TableName("wis_recharge_times_config")
@Data
@ApiModel("充值次数设置PO实体类")
@Accessors(chain = true)
public class RechargeTimesConfigPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 性别
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 开始日期
     */
    @TableField(value = "begin_time")
    @ApiModelProperty(value = "开始日期")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 时间
     */
    @TableField(value = "time")
    @ApiModelProperty(value = "时间")
    private Integer time;

    /**
     * 价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 次数
     */
    @TableField(value = "times")
    @ApiModelProperty(value = "次数")
    private Integer times;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 二维码
     */
    @TableField(value = "qr_code")
    @ApiModelProperty(value = "二维码")
    private String qrCode;

    /**
     * 状态
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态")
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}