package com.diandong.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 健康证DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("健康证DTO实体类")
public class HealthCertificateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 健康证图片
     */
    @ApiModelProperty(value = "健康证图片")
    private String healthCertPic;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 有效期开始时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "有效期开始时间")
    private LocalDateTime validityStartTime;

    /**
     * 有效期结束时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "到期时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期结束时间")
    private LocalDateTime validityEndTime;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @ApiModelProperty(value = "编号")
    private String code;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /**
     * 出生日期
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "0=停用,1=启用")
    @ApiModelProperty(value = "状态")
    private Integer state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}