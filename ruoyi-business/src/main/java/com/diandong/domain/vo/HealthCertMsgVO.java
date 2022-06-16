package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 健康证到期消息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("健康证到期消息VO实体类")
public class HealthCertMsgVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 健康证id
     */
    @ApiModelProperty(value = "健康证id")
    private Long healthCertId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 预警时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "validity_end_time")
    @ApiModelProperty(value = "预警时间",notes = "2022-6-14 00:00:00")
    private LocalDateTime warningTime;

    /**
     * 到期前提醒
     */
    @ApiModelProperty(value = "到期前提醒")
    private String remindDay;

    /**
     * 状态 0：未读；1：已读
     */
    @ApiModelProperty(value = "状态 0：未读；1：已读")
    private Integer state;


}