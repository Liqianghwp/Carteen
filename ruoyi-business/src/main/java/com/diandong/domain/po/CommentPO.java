package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评价处理PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_comment")
@Data
@ApiModel("评价处理PO实体类")
@Accessors(chain = true)
public class CommentPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    /**
     * 评价内容
     */
    @TableField(value = "comment_content")
    @ApiModelProperty(value = "评价内容")
    private String commentContent;

    /**
     * 评价图片 图片路径
     */
    @TableField(value = "comment_picture")
    @ApiModelProperty(value = "评价图片 图片路径")
    private String commentPicture;

    /**
     * 喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @TableField(value = "delicious_dishes")
    @ApiModelProperty(value = "喜好菜品 这个地方存储的应该是菜品id的集合")
    private String deliciousDishes;

    /**
     * 不喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @TableField(value = "undelicious_dishes")
    @ApiModelProperty(value = "不喜好菜品 这个地方存储的应该是菜品id的集合")
    private String undeliciousDishes;

    /**
     * 处理描述
     */
    @TableField(value = "process_description")
    @ApiModelProperty(value = "处理描述")
    private String processDescription;

    /**
     * 处理状态 0：未处理；1：处理中；2：已处理
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "处理状态 0：未处理；1：处理中；2：已处理")
    private Integer status;

    /**
     * 处理时间
     */
    @TableField(value = "process_time")
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态 0：未删除；1：已删除
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态 0：未删除；1：已删除")
    private Integer delFlag;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间 默认当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间 这个可与处理时间一致
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间 这个可与处理时间一致")
    private LocalDateTime updateTime;

}