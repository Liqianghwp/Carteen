package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审核列PO实体类
 *
 * @author YuLiu
 * @date 2022-06-21
 */
@TableName("wis_review_list")
@Data
@ApiModel("审核列PO实体类")
@Accessors(chain = true)
public class ReviewListPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    @ApiModelProperty(value = "业务id")
    private Long businessId;

    /**
     * 审批单id
     */
    @TableField(value = "apply_id")
    @ApiModelProperty(value = "审批单id")
    private String applyId;

    /**
     * 审批状态
     */
    @TableField(value = "apply_status")
    @ApiModelProperty(value = "审批状态")
    private Integer applyStatus;

    /**
     * 审批步骤类型
     */
    @TableField(value = "apply_step_type")
    @ApiModelProperty(value = "审批步骤类型")
    private Integer applyStepType;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 审批完成时间
     */
    @TableField(value = "finish_time")
    @ApiModelProperty(value = "审批完成时间")
    private LocalDateTime finishTime;

    /**
     * 审批意见
     */
    @TableField(value = "apply_opinion")
    @ApiModelProperty(value = "审批意见")
    private String applyOpinion;

    /**
     * 审批顺序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "审批顺序")
    private Integer sort;

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