package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 食堂采购PO实体类
 *
 * @author YuLiu
 * @date 2022-06-20
 */
@TableName("wis_canteen_purchase")
@Data
@ApiModel("食堂采购PO实体类")
@Accessors(chain = true)
public class CanteenPurchasePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

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
     * 食谱日期开始时间
     */
    @TableField(value = "recipe_start_date")
    @ApiModelProperty(value = "食谱日期开始时间")
    private LocalDate recipeStartDate;

    /**
     * 食谱日期结束时间
     */
    @TableField(value = "recipe_end_date")
    @ApiModelProperty(value = "食谱日期结束时间")
    private LocalDate recipeEndDate;

    /**
     * 有效日期
     */
    @TableField(value = "valid_date")
    @ApiModelProperty(value = "有效日期")
    private String validDate;

    /**
     * 天数
     */
    @TableField(value = "days")
    @ApiModelProperty(value = "天数")
    private Integer days;

    /**
     * 审核状态 (0:未提交;1:审核中;2:审核通过;3:审核驳回;)
     */
    @TableField(value = "state")
    @ApiModelProperty(value = "审核状态 (0:未提交;1:审核中;2:审核通过;3:审核驳回;)")
    private Integer state;

    /**
     * 审核id
     */
    @TableField(value = "apply_id")
    @ApiModelProperty(value = "审核id")
    private String applyId;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 提交时间
     */
    @TableField(value = "submit_time")
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;

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