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
 * @date 2022-03-23
 */
@TableName("canteen")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class CanteenPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    /**
     * 食堂图片，存储的是路径地址
     */
    @TableField(value = "canteen_picture")
    @ApiModelProperty(value = "食堂图片，存储的是路径地址")
    private String canteenPicture;

    /**
     * 食堂介绍
     */
    @TableField(value = "canteen_introduce")
    @ApiModelProperty(value = "食堂介绍")
    private String canteenIntroduce;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
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