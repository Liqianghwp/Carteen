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
@ApiModel("评价DTO实体类")
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容")
    private String commentContent;

    /**
     * 评价图片 图片路径
     */
    @ApiModelProperty(value = "评价图片 图片路径")
    private String commentPicture;

    /**
     * 喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @ApiModelProperty(value = "喜好菜品 这个地方存储的应该是菜品id的集合")
    private String deliciousDishes;

    /**
     * 不喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @ApiModelProperty(value = "不喜好菜品 这个地方存储的应该是菜品id的集合")
    private String undeliciousDishes;

    /**
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    private String processDescription;

    /**
     * 处理状态 0：未处理；1：处理中；2：已处理
     */
    @ApiModelProperty(value = "处理状态 0：未处理；1：处理中；2：已处理")
    private Integer status;

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
     * 创建时间 默认当前时间
     */
    @ApiModelProperty(value = "创建时间 默认当前时间")
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
     * 更新时间 这个可与处理时间一致
     */
    @ApiModelProperty(value = "更新时间 这个可与处理时间一致")
    private LocalDateTime updateTime;

}