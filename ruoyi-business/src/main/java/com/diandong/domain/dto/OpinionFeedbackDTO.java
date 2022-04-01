package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO实体类
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Data
@ApiModel("DTO实体类")
public class OpinionFeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 意见类型id
     */
    @ApiModelProperty(value = "意见类型id")
    private Long opinionId;

    /**
     * 意见类型
     */
    @ApiModelProperty(value = "意见类型")
    private String opinionType;

    /**
     * 意见内容
     */
    @ApiModelProperty(value = "意见内容")
    private String opinionContent;

    /**
     * 图片 图片地址
     */
    @ApiModelProperty(value = "图片 图片地址")
    private String opinionPicture;

    /**
     * 处理信息 可以用text类型
     */
    @ApiModelProperty(value = "处理信息 可以用text类型")
    private String processInformation;

    /**
     * 状态 0：未处理；1：已查看；2：已处理
     */
    @ApiModelProperty(value = "状态 0：未处理；1：已查看；2：已处理")
    private Integer status;

    /**
     * 匿名状态 0:不匿名；1:匿名
     */
    @ApiModelProperty(value = "匿名状态 0:不匿名；1:匿名")
    private Integer anonymous;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态 0：未删除；1：已删除
     */
    @ApiModelProperty(value = "数据状态 0：未删除；1：已删除")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}