package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 意见反馈PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_opinion_feedback")
@Data
@ApiModel("意见反馈PO实体类")
@Accessors(chain = true)
public class OpinionFeedbackPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @Excel(sort = 1, name = "食堂名称")
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 意见类型id
     */
    @TableField(value = "opinion_id")
    @ApiModelProperty(value = "意见类型id")
    private Long opinionId;

    /**
     * 意见类型
     */
    @Excel(sort = 2, name = "意见类型")
    @TableField(value = "opinion_type")
    @ApiModelProperty(value = "意见类型")
    private String opinionType;

    /**
     * 意见内容
     */
    @Excel(sort = 4, name = "内容")
    @TableField(value = "opinion_content")
    @ApiModelProperty(value = "意见内容")
    private String opinionContent;

    /**
     * 图片 图片地址
     */
    @TableField(value = "opinion_picture")
    @ApiModelProperty(value = "图片 图片地址")
    private String opinionPicture;

    /**
     * 处理信息 可以用text类型
     */
    @TableField(value = "process_information")
    @ApiModelProperty(value = "处理信息 可以用text类型")
    private String processInformation;

    /**
     * 状态 0：未处理；1：已查看；2：已处理
     */
    @Excel(sort = 6, name = "手机号",readConverterExp = "0=未处理,1=已查看,2=已处理")
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 0：未处理；1：已查看；2：已处理")
    private Integer status;


    /**
     * 匿名状态 0:不匿名；1:匿名
     */
    @TableField(value = "anonymous")
    @ApiModelProperty(value = "匿名状态 0:不匿名；1:匿名")
    private Integer anonymous;

    /**
     * 处理人id
     */
    @TableField(value = "handler_id")
    @ApiModelProperty(value = "处理人id")
    private Long handlerId;
    /**
     * 处理时间
     */
    @TableField(value = "process_time")
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @Excel(sort = 3, name = "反馈时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @Excel(sort = 5, name = "用户名称")
    @TableField(exist = false)
    @ApiModelProperty(value = "用户名称")
    private String createName;

    @Excel(sort = 6, name = "手机号")
    @TableField(exist = false)
    @ApiModelProperty(value = "手机号")
    private String phone;

}