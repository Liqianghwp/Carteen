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
 * @date 2022-06-08
 */
@Data
@ApiModel("DTO实体类")
public class TransferCommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 调拨表id
     */
    @ApiModelProperty(value = "调拨表id")
    private Long transferId;

    /**
     * 台账id
     */
    @ApiModelProperty(value = "台账id")
    private Long rid;

    /**
     * 调拨数量
     */
    @ApiModelProperty(value = "调拨数量")
    private Long number;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer felFlag;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间默认当前时间
     */
    @ApiModelProperty(value = "创建时间默认当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}