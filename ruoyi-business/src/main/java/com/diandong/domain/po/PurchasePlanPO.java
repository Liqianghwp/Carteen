package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购计划单PO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@TableName("wis_purchase_plan")
@Data
@ApiModel("采购计划单PO实体类")
@Accessors(chain = true)
public class PurchasePlanPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 采购计划单id
     */
    @TableField(value = "plan_id")
    @ApiModelProperty(value = "采购计划单id")
    private String planId;

    /**
     * 审批单id
     */
    @TableField(value = "apply_id")
    @ApiModelProperty(value = "审批单id")
    private String applyId;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 食谱日期
     */
    @TableField(value = "recipe_date")
    @ApiModelProperty(value = "食谱日期")
    private String recipeDate;

    /**
     * 总额
     */
    @TableField(value = "total")
    @ApiModelProperty(value = "总额")
    private BigDecimal total;

    /**
     * 审核状态 0：未提交；1：审核中；2：审核通过；3：审核驳回
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "审核状态 0：未提交；1：审核中；2：审核通过；3：审核驳回")
    private Integer state;

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