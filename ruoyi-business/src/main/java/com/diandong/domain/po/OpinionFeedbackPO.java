package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@TableName("wis_opinion_feedback")
@Data
@ApiModel("PO实体类")
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
     * 意见类型id
     */
    @TableField(value = "opinion_id")
    @ApiModelProperty(value = "意见类型id")
    private Long opinionId;

    /**
     * 意见类型
     */
    @TableField(value = "opinion_type")
    @ApiModelProperty(value = "意见类型")
    private String opinionType;

    /**
     * 意见内容
     */
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
     * 处理时间
     */
    @TableField(value = "process_time")
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态 0：未处理；1：已查看；2：已处理
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态 0：未处理；1：已查看；2：已处理")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}