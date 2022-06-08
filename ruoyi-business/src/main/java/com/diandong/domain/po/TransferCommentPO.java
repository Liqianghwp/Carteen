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
 * @date 2022-06-08
 */
@TableName("wis_transfer_comment")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class TransferCommentPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 调拨表id
     */
    @TableField(value = "transfer_id")
    @ApiModelProperty(value = "调拨表id")
    private Long transferId;

    /**
     * 台账id
     */
    @TableField(value = "rid")
    @ApiModelProperty(value = "台账id")
    private Long rid;

    /**
     * 调拨数量
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "调拨数量")
    private Long number;

    /**
     * 数据状态
     */
    @TableField(value = "fel_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer felFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间默认当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间默认当前时间")
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