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
@TableName("wis_transfer")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class TransferPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "序号id")
    private Long id;

    /**
     * 单据编号
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "单据编号")
    private String code;

    /**
     * 出库食堂
     */
    @TableField(value = "outbound_canteen")
    @ApiModelProperty(value = "出库食堂")
    private String outboundCanteen;

    /**
     * 入库食堂
     */
    @TableField(value = "inbound_canteen")
    @ApiModelProperty(value = "入库食堂")
    private String inboundCanteen;

    /**
     * 状态
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 审核人
     */
    @TableField(value = "reviewer")
    @ApiModelProperty(value = "审核人")
    private String reviewer;

    /**
     * 审核时间
     */
    @TableField(value = "review_time")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}