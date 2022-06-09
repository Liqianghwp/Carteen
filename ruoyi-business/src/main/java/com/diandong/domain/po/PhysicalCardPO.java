package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实物卡PO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@TableName("wis_physical_card")
@Data
@ApiModel("实物卡PO实体类")
@Accessors(chain = true)
public class PhysicalCardPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 卡号
     */
    @TableField(value = "card_no")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 停用启用 0：停用；1：启用
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "停用启用 0：停用；1：启用")
    private Integer state;

    /**
     * 是否挂失 0：否；1：是
     */
    @TableField(value = "report_loss")
    @ApiModelProperty(value = "是否挂失 0：否；1：是")
    private Integer reportLoss;

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
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}